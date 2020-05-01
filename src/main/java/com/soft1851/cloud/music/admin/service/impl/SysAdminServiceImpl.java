package com.soft1851.cloud.music.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.soft1851.cloud.music.admin.common.ResultCode;
import com.soft1851.cloud.music.admin.dto.SignDto;
import com.soft1851.cloud.music.admin.entity.SysAdmin;
import com.soft1851.cloud.music.admin.entity.SysRole;
import com.soft1851.cloud.music.admin.exception.CustomException;
import com.soft1851.cloud.music.admin.mapper.SysAdminMapper;
import com.soft1851.cloud.music.admin.mapper.SysRoleMapper;
import com.soft1851.cloud.music.admin.service.RedisService;
import com.soft1851.cloud.music.admin.service.SysAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft1851.cloud.music.admin.util.JwtTokenUtil;
import com.soft1851.cloud.music.admin.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wf
 * @since 2020-04-21
 */
@Service
@Slf4j
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin> implements SysAdminService {
    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private RedisService redisService;

    @Override
    public Map<String, Object> sign(SignDto signDto) {
        SysAdmin admin = getAdminByName(signDto.getName());
        Map<String, Object> data = new LinkedHashMap<>();
        Map<String, Object> adminMap = new LinkedHashMap<>();
        //构建user信息（同下）
        adminMap.put("userId", admin.getId());
        adminMap.put("name", admin.getName());
        adminMap.put("avatar", admin.getAvatar());
        List<Map<String, Object>> maps = getAdminRoleByAdminName(signDto.getName());
        List<SysRole> roles = new ArrayList<>();
        //构建role角色
        for (Map<String, Object> map : maps) {
            SysRole sysRole = SysRole.builder()
                    .roleId(Integer.parseInt(map.get("role_id").toString()))
                    .roleName(map.get("role_name").toString())
                    .build();
            roles.add(sysRole);
        }
        //将私钥存入redis
        redisService.set(admin.getId(), admin.getSalt());
        String password = Md5Util.getMd5(signDto.getPassword(), true, 32);
        log.info("角色字符串位：" + JSONObject.toJSONString(roles));
        //对token序列化成字符串

        String token = JwtTokenUtil.getToken( admin.getId(), JSONObject.toJSONString(roles),admin.getSalt(), new Date(System.currentTimeMillis() + 6000L * 1000));
        log.info("" + String.valueOf(roles));
        if (admin.getPassword().equals(password)) {
            data.put("user", adminMap);
            data.put("roles", roles);
            data.put("token", token);
            return data;
        }
        throw new CustomException("账号密码错误", ResultCode.USER_PASSWORD_ERROR);
    }

    @Override
    public SysAdmin getAdminByName(String name) {
        QueryWrapper<SysAdmin> wrapper = new QueryWrapper<>();
        wrapper.eq("name", name);
        SysAdmin sysAdmin = sysAdminMapper.selectOne(wrapper);
        if (sysAdmin != null) {
            return sysAdmin;
        }
        throw new CustomException("用户不存在", ResultCode.USER_NOT_FOUND);
    }

    @Override
    public List<Map<String, Object>> getAdminRoleByAdminName(String name) {
        List<Map<String, Object>> maps = sysAdminMapper.getAdminRoleByAdminName(name);
        if (maps != null) {
            return maps;
        }
        throw new CustomException("用户角色查询异常", ResultCode.DATABASE_ERROR);
    }

    @Override
    public void updateInfo(SysAdmin admin) {
        UpdateWrapper<SysAdmin> wrapper = new UpdateWrapper<>();
        wrapper.set("name", admin.getName())
                .set("avatar", admin.getAvatar())
                .set("status", admin.getStatus());
        try {
            sysAdminMapper.update(admin, wrapper);
        } catch (Exception e) {
            throw new CustomException("个人信息修改异常", ResultCode.DATABASE_ERROR);
        }
    }

    @Override
    public void insertSingle(SysAdmin admin) {
        try {
            sysAdminMapper.insert(admin);
        } catch (Exception e) {
            throw new CustomException("用户新增异常", ResultCode.DATABASE_ERROR);
        }
    }

    @Override
    public void batchDelete(String id) {
        String[] strings = id.split(",");
        List<String> idList = Arrays.asList(strings);
        try {
            sysAdminMapper.deleteBatchIds(idList);
        } catch (Exception e) {
            throw new CustomException("用户批量删除异常", ResultCode.DATABASE_ERROR);
        }
    }

    @Override
    public Map<String, Object> selectAll() {
        List<SysRole> roles = sysRoleMapper.selectList(new QueryWrapper<>());
        List<Map<String, Object>> maps = sysAdminMapper.selectAll();
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("admin", maps);
        map.put("roles", roles);
        if(maps != null){
            return map;
        }
        throw new CustomException("未查到用户信息", ResultCode.DATA_IS_WRONG);
    }
}
