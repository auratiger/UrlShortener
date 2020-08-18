import {initStore} from './store';
import {SET_CURRENT_ORGANIZATION, LOGOUT_ORGANIZATION} from './actionTypes';

const configureStore = () => {
    const actions = {
        SET_CURRENT_ORGANIZATION: (curState, payload) => {
            let authenticated = payload ? true : false;
            // let name = payload.name;

            let obj = JSON.parse(payload.obj);

            return {
                ...curState,
                "isAuthenticated": authenticated,
                ...obj
            }
        },
        LOGOUT_ORGANIZATION: (curState) => {
            localStorage.removeItem("jwtToken");
            return{
                ...curState,
                "isAuthenticated": false,
            }
        }
        
    };

    initStore(actions, {});
};

export default configureStore;