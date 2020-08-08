import React, {useState} from 'react';
import {withRouter} from 'react-router-dom'

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

    const [credentials, setCredentials] = useState({
        email: "",
        password: "",
    })

    const handleInputChange = (event) => {
        setCredentials({
            ...credentials,
            [event.target.name]: event.target.value
        })
    }

    const handleSubmit = (event) => {
        event.preventDefault();

        const user = {
            email: credentials.email,
            password: credentials.password,
        }

        // TODO: axios request
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
                            onChange={handleInputChange}/> 
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
                            onChange={handleInputChange}/>  
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