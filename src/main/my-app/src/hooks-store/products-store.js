import {initStore} from './store';

const configureStore = () => {
    const actions = {
        TEST: curState => {
            console.log("hello");
            return {"test": "really"}
        }
        
    };

    initStore(actions, {});
};

export default configureStore;