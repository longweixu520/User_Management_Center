// @ts-nocheck
// This file is generated by Umi automatically
// DO NOT CHANGE IT MANUALLY!
import React from 'react';

export async function getRoutes() {
  const routes = {"1":{"path":"/user","layout":false,"id":"1"},"2":{"name":"login","path":"/user/login","parentId":"1","id":"2"},"3":{"path":"/welcome","name":"welcome","icon":"smile","parentId":"ant-design-pro-layout","id":"3"},"4":{"path":"/admin","name":"admin","icon":"crown","access":"canAdmin","parentId":"ant-design-pro-layout","id":"4"},"5":{"path":"/admin","redirect":"/admin/sub-page","parentId":"4","id":"5"},"6":{"path":"/admin/sub-page","name":"sub-page","parentId":"4","id":"6"},"7":{"name":"list.table-list","icon":"table","path":"/list","parentId":"ant-design-pro-layout","id":"7"},"8":{"path":"/","redirect":"/welcome","parentId":"ant-design-pro-layout","id":"8"},"9":{"path":"*","layout":false,"id":"9"},"ant-design-pro-layout":{"id":"ant-design-pro-layout","path":"/","isLayout":true}} as const;
  return {
    routes,
    routeComponents: {
'1': require('./EmptyRoute').default,
'2': require('@/pages/User/Login/index.tsx').default,
'3': require('@/pages/Welcome.tsx').default,
'4': require('./EmptyRoute').default,
'5': require('./EmptyRoute').default,
'6': require('@/pages/Admin.tsx').default,
'7': require('@/pages/TableList/index.tsx').default,
'8': require('./EmptyRoute').default,
'9': require('@/pages/404.tsx').default,
'ant-design-pro-layout': require('C:/Users/Jiang Xiaoqun/Desktop/myapp/src/.umi-test/plugin-layout/Layout.tsx').default,
},
  };
}
