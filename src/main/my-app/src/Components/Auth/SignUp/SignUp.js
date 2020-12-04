import React, {useState} from 'react';
import useForm from '../../../hooks/useForm';
import { withRouter } from "react-router-dom";

import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import Grid from '@material-ui/core/Grid';

import axios from 'axios';

import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    root: {
        margin: "5% auto",
        width: "30%",
        textAlign: "center",
        boxShadow: "0 2px 3px #ccc",
        border: "1px solid #eee",
        padding: "20px",
        boxSizing: "border-box",
    },
  }));

const initialState = {
    name     : "",
    email    : "",
    password : "",
    confirm  : "",
}

const validationState = {
    name     : false,
    email    : false,
    password : false,
    confirm  : false,
}

const SignUp = (props) => {

    const [credentials, handleChange, setCredentials] = useForm(initialState);
    const [areValid, setAreValid] = useState(validationState);

    const classes = useStyles();

    const messagesValidateHandler = (event) => {
        const name = event.target.name;
        const text = event.target.value;
        let valid = false;
        let patt;

        switch(name){
            case "email":
                patt = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
                valid = patt.test(text);     
                break;
            case "confirm":
                if(text === credentials.password){
                    valid = true;
                }
                break;
            default:
                patt = /.{1,20}/;
                valid = patt.test(text);
        }

        setAreValid({
            ...areValid,
            [name]: valid
        });

        handleChange(event);
    }

    const handleSubmit = (event) => {   

        event.preventDefault()
        
        //TODO the data should be encrypted before sending

        let valid = true;

        Object.values(areValid).forEach(item => {
            if(!item){
                valid = false;
            }
        })

        if(!valid) return;

        const obj = {
            name: credentials.name,
            email: credentials.email,
            password: credentials.password,
        };   
        
        axios.post('http://localhost:8081/auth/signup', obj)
        .then((response) => {
          console.log(response.data);  
          props.history.push("/urls");
        }).catch((err) => {
          console.log(err);
        });

        setCredentials(initialState);
    }
    
    return(
        <div className={classes.root}>
            <h1>Register Organization</h1>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <TextField 
                            error={!areValid.name}
                            fullWidth
                            id="name" 
                            label="Organization Name*"
                            name="name"
                            variant="outlined"
                            helperText={areValid.name ? "" : "limit up to 20 characters"}
                            value={credentials.name}
                            onChange={messagesValidateHandler}/>   
                    </Grid>

                    <Grid item xs={12}>
                        <TextField 
                            error={!areValid.email}
                            fullWidth
                            id="email" 
                            label="Email*"
                            name="email"
                            type="email"
                            variant="outlined"
                            helperText={areValid.email ? "" : "email is not valid"}
                            value={credentials.email}
                            onChange={messagesValidateHandler}/>    
                    </Grid>

                    <Grid item xs={6}>
                        <TextField 
                            error={!areValid.password}
                            fullWidth
                            id="password" 
                            label="Password*"
                            name="password"
                            type="password"
                            variant="outlined"
                            helperText={areValid.password ? "" : "limit up to 20 characters"}
                            value={credentials.password}
                            onChange={messagesValidateHandler}/>    
                    </Grid>

                    <Grid item xs={6}>
                        <TextField 
                            error={!areValid.confirm}
                            fullWidth
                            id="confpassword" 
                            label="Confirm password*"
                            name="confirm"
                            type="password"
                            variant="outlined"
                            helperText={areValid.confirm ? "" : "limit up to 20 characters"}
                            value={credentials.confirm}
                            onChange={messagesValidateHandler}/>   
                    </Grid>
                </Grid> 

                <br/>

                <ButtonGroup
                    orientation="vertical"
                    color="primary"
                    aria-label="vertical outlined primary button group">
                    
                    <Button variant="outlined" color="primary" size='medium' onClick={handleSubmit}>
                        Submit
                    </Button>
                    
                    <Button variant="outlined" 
                            color="primary" 
                            size='medium' 
                            onClick={() => props.history.push("/auth/login")}>
                        Log in 
                    </Button>

                </ButtonGroup> 
                <button style={{display: "none"}}></button>
            </form>              
        </div>
    )
}


export default withRouter(SignUp);