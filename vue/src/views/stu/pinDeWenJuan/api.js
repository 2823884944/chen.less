import { getRequest} from '@/libs/axios';

export const saveOneByType = (params) => {
    return getRequest('/questionnaire/saveOneByType', params)
}
export const getOneByType = (params) => {
    return getRequest('/questionnaire/getOneByType', params)
}
export const editPinDeZiPing = (params) => {
    return getRequest('/studentEvaluate/editPinDeZiPing', params)
}