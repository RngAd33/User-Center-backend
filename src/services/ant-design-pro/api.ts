// @ts-ignore
/* eslint-disable */
import request from '@/plugins/globalRequest';
import {List} from "antd";

/** 注册接口 POST /user/register */
export async function register(body: API.RegisterParams, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.RegisterResult>>('/user/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 登录接口 POST /user/login */
export async function login(body: {
  userPassword: string | undefined;
  userName: string | number | boolean | null | Object | List
}, options?: { [p: string]: any }) {
  return request<API.BaseResponse<API.LoginResult>>('/user/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取当前用户 GET /user/current */
export async function currentUser(options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.CurrentUser>>('/user/current', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录 POST /user/logout */
export async function outLogin(options?: { [key: string]: any }) {
  return request<API.BaseResponse<number>>('/user/logout', {
    method: 'POST',
    ...(options || {}),
  });
}


/** 用户查询 GET /user/admin/search */
export async function searchUsers(options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.CurrentUser[]>>('/user/admin/search', {
    method: 'GET',
    ...(options || {}),
  });
}
