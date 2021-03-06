import React, {useState} from 'react';
import useForm from '../../../hooks/useForm';
import {withRouter} from 'react-router-dom'
import axios from 'axios';

import {SET_CURRENT_ORGANIZATION} from '../../../hooks-store/actionTypes';
import {useStore} from '../../../hooks-store/store';
import {setAuthToken} from '../../../setAuthToken';
import parseJwt from './../../../jwtParser/jwtParser';

import Button from '@material-ui/core/Button';
import ButtonGroup from '@material-ui/core/ButtonGroup';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';

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

const Login = (props) => {

    const classes = useStyles();

    const dispatch = useStore()[1];

    const [credentials, handleChange] = useForm({
        email: "",
        password: "",
    })

    const handleSubmit = (event) => {
        event.preventDefault();

        const user = {
            email: credentials.email,
            password: credentials.password,
        }

        let config = {
            headers: {
              "Access-Control-Allow-Credentials": true,
            },
            credentials: 'include'
          }

        // TODO: axios request
        axios.post("http://localhost:8081/auth/login", user, config)
            .then(response => {
                const token = response.data;
                localStorage.setItem("jwtToken", token);
                setAuthToken(token);
                const decoded = parseJwt(token);
                dispatch(SET_CURRENT_ORGANIZATION, decoded);
                props.history.push("/");
            })
            .catch(error => {
                console.log(error);
            });
        
    }

    return(
        <div className={classes.root}>
            <h1>Log In</h1>
            <form onSubmit={handleSubmit}>
                <Grid container spacing={1}>
                    <Grid item xs={12}>
                        <TextField 
                            fullWidth
                            id="email" 
                            label="Email*"
                            name="email"
                            type="email"
                            variant="outlined"
                            value={credentials.email}
                            onChange={handleChange}/> 
                    </Grid>

                    <Grid item xs={12}>
                        <TextField 
                            fullWidth
                            id="password" 
                            label="Password*"
                            name="password"
                            type="password"
                            variant="outlined"
                            value={credentials.password}
                            onChange={handleChange}/>  
                    </Grid>
                </Grid>

                <br/>

                <ButtonGroup
                    orientation="vertical"
                    color="primary"
                    aria-label="vertical outlined primary button group">

                    <Button variant="outlined" 
                            color="primary" 
                            size='medium' 
                            onClick={handleSubmit}>
                        Submit
                    </Button>

                    <Button variant="outlined" 
                            color="primary" 
                            size='medium' 
                            onClick={() => props.history.push("/auth/signup")}>
                        Create account
                    </Button> 

                </ButtonGroup>             
                <button style={{display: "none"}}></button>
            </form>
        </div>
    )
}


export default withRouter(Login);