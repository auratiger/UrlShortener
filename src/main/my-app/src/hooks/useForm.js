import { useState } from 'react';

const useForm = (initialValues) => {

    const [state, setState] = useState(initialValues);

    const handleChange = e => {  
        e.persist();
        setState(state => ({
            ...state,
            [e.target.name]: e.target.value,
        }))
    }

    return [state, handleChange, setState];
}

export default useForm;