import { getRequest} from '@/libs/axios';

export const getLogListData = (params) => {
    return getRequest('/log/getAllByPage', params)
}