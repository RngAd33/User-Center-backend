/**
 * request 网络请求工具
 * 更详细的 api 文档: https://github.com/umijs/umi-request
 */
import {extend} from 'umi-request';
import {message} from "antd";
import {history} from "@@/core/history";
import {stringify} from "querystring";

/**
 * 配置request请求时的默认参数
 */
const request = extend({
  credentials: 'include', // 默认请求是否带上cookie
  prefix: process.env.NODE_ENV === 'production' ? 'http://user-backend.code-nav.cn' : 'http://localhost:8080',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * 所有请求拦截器
 */
request.interceptors.request.use((url, options): any => {
  console.log(`do request url = ${url}`)
  console.log('request options:', options)
  console.log('request body:', options.data)

  // 去除请求体中的多余字段
  if (options.data) {
    const { userName, userPassword } = options.data;
    options.data = { userName, userPassword };
  }

  return {
    url,
    options: {
      ...options,
      method: options.method?.toUpperCase(),
      headers: {
        'Content-Type': 'application/json',
        ...options.headers,
      },
    },
  };
});

/**
 * 所有响应拦截器
 */
request.interceptors.response.use(async (response, options): Promise<any> => {
  try {
    const res = await response.clone().json();
    console.log('response data:', res);

    // 处理后端返回的不同状态码
    if (res.code === 0 || res.code === 200) {
      return res.data;
    }

    if (res.code === 40100) {
      message.error('请先登录！');
      history.replace({
        pathname: '/user/login',
        search: stringify({
          redirect: location.pathname,
        }),
      });
    } else if (res.code === 4048) {
      // 处理登录失败
      message.error(res.msg || '用户名或密码错误！');
    } else {
      message.error(res.msg || res.description || '请求失败，请重试！')
    }
    return null;
  } catch (error) {
    console.error('请求错误:', error);
    message.error('请求失败，请检查网络连接！');
    return null;
  }
});

export default request;
