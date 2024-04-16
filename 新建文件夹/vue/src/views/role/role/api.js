import { getRequest, postRequest} from '@/libs/axios';


// 分页获取角色数据
export const getRoleList = (params) => {
    return getRequest('/role/getAllByPage', params)
}
// 添加角色
export const addRole = (params) => {
    return postRequest('/role/save', params)
}
// 编辑角色
export const editRole = (params) => {
    return postRequest('/role/edit', params)
}
// 获取全部权限数据
export const getAllPermissionList = (params) => {
    return getRequest('/permission/getAllList', params)
}
export const deleteRole = (params) => {
    return postRequest('/role/delByIds', params)
}
export const setDefaultRole = (params) => {
    return postRequest('/role/setDefault', params)
}
export const editRolePerm = (params) => {
    return postRequest('/role/editRolePerm', params)
}
// 获取一级组织
export const initDepartment = (params) => {
    return getRequest('/department/getByParentId', params)
}
// 加载组织子级数据
export const loadDepartment = (params) => {
    return getRequest('/department/getByParentId', params)
}