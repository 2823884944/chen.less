import { getRequest} from '@/libs/axios';

export const getAntvVoList = (params) => {
    return getRequest('/teacher/getAntvVoList', params)
}