import React, {useState} from 'react';
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

const SignUp = (props) => {

    const [user, setUser] = useState({
        organizationName : {text: "", valid: false},
        email    : {text: "", valid: false},
        password : {text: "", valid: false},
        confirm  : {text: "", valid: false},
    });

    const [redirect, setRedirect] = useState(false);
    const classes = useStyles();

    const setupUser = () => {
        setUser({
            organizationName : {text: "", valid: false},
            email    : {text: "", valid: false},
            password : {text: "", valid: false},
            confirm  : {text: "", valid: false},
        })
    }

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
                if(text === user.password.text){
                    valid = true;
                }
                break;
            default:
                patt = /.{1,20}/;
                valid = patt.test(text);
        }

        setUser({
            ...user,
            [name]: {"text": text, "valid": valid},
        })
    }

    const handleSubmit = (event) => {   

        event.preventDefault()
        
        //TODO the data should be encrypted before sending

        let valid = true;

        Object.values(user).forEach(item => {
            if(!item.valid){
                valid = false;
            }
        })

        if(!valid) return;

        const obj = {
            organizationName: user.organizationName.text,
            email: user.email.text,
            password: user.password.text,
        };   
        
        // axios.post('http://localhost:8081/auth/signup', obj)
        // .then((response) => {
        //   console.log(response.data);  
        //   props.history.push("/");
        // }).catch((err) => {
        //   console.log(err);
        // });

        setupUser();
        setRedirect(false);
    }
    
    return(
        <div className={classes.root}>
            <h1>Register Organization</h1>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <TextField 
                            error={!user.organizationName.valid}
                            fullWidth
                            id="organizationName" 
                            label="Organization Name*"
                            name="organizationName"
                            variant="outlined"
                            helperText={user.organizationName.valid ? "" : "limit up to 20 characters"}
                            value={user.organizationName.text}
                            onChange={messagesValidateHandler}/>   
                    </Grid>

                    <Grid item xs={12}>
                        <TextField 
                            error={!user.email.valid}
                            fullWidth
                            id="email" 
                            label="Email*"
                            name="email"
                            type="email"
                            variant="outlined"
                            helperText={user.email.valid ? "" : "email is not valid"}
                            value={user.email.text}
                            onChange={messagesValidateHandler}/>    
                    </Grid>

                    <Grid item xs={6}>
                        <TextField 
                            error={!user.password.valid}
                            fullWidth
                            id="password" 
                            label="Password*"
                            name="password"
                            type="password"
                            variant="outlined"
                            helperText={user.password.valid ? "" : "limit up to 20 characters"}
                            value={user.password.text}
                            onChange={messagesValidateHandler}/>    
                    </Grid>

                    <Grid item xs={6}>
                        <TextField 
                            error={!user.confirm.valid}
                            fullWidth
                            id="confpassword" 
                            label="Confirm password*"
                            name="confirm"
                            type="password"
                            variant="outlined"
                            helperText={user.confirm.valid ? "" : "limit up to 20 characters"}
                            value={user.confirm.text}
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