import { getRequest} from '@/libs/axios';

export const getOneByStudent = (params) => {
    return getRequest('/studentEvaluate/getOneByStudent', params)
}