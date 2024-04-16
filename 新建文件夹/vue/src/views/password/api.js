import { postRequest} from '@/libs/axios';

export const changePass = (params) => {
    return postRequest('/user/modifyPass', params)
}