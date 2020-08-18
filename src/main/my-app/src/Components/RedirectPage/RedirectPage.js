import React, {useState} from 'react';
import { useStore } from '../../hooks-store/store';
import {withRouter} from 'react-router-dom'

import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

import axios from 'axios';

import ContainerAppBar from '../ContairnerAppBar/ContainerAppBar';

const useStyles = makeStyles((theme) => ({
    root: {
        '& > *': {
          margin: theme.spacing(1),
    }},
    paper: {
        marginTop: theme.spacing(5),
        paddingBottom: theme.spacing(5),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
    input: {
        width: "50%"
    },
    form: {
        width: '100%',
        marginTop: theme.spacing(1),
    },
}));

const RedirectPage = (props) => {

    const classes = useStyles();
    const [slug, setSlug] = useState("");
    const [organizationTab, setOrganizationTab] = useState(false);
    const [state, dispatch] = useStore();

    const onSlugChangeHandler = (event) => {
        setSlug(event.target.value)
    }

    const handleSubmit = () => {

        if(slug === null || slug.trim().length < 1){
            return;
        }

        axios.get('http://localhost:8081/urls/'+slug)
            .then(response => {
                console.log(response.data);
                window.location.href = response.data;
            })
            .catch(err => {
                console.log(err);
            })
    }

    return(
        <Grid container>
        <Grid item xs={12}>
            <h1 className={classes.paper}></h1>
        </Grid>
        <Grid item xs={4}></Grid>
        <Grid item xs={4}>
            <Paper className={classes.paper} elevation={3}>
                <ContainerAppBar 
                        state={state} 
                        subject={(state.isAuthenticated) ? 
                            <p>Organization shortener</p> :
                            <p style={{color: "red", padding: 0, margin: 0}}>You must register as an organization to use!</p>}
                        setOrganizationTab={setOrganizationTab}/>
                <form className={classes.form}>
                    <div>
                        <TextField className={classes.input} label={"Slug"} value={slug} onChange={onSlugChangeHandler} />
                    </div>
                    <br/>
                    {/* <Button onClick={handleSubmit} variant="contained">Redirect</Button> */}
                                
                    <div className={classes.root}>
                        <Button variant="outlined" 
                                color="primary" 
                                size='medium' 
                                onClick={handleSubmit}>
                            Redirect
                        </Button>

                        <Button variant="outlined" 
                                color="primary" 
                                size='medium' 
                                onClick={() => props.history.push("redirectPage/list")}>
                            List all
                        </Button> 
                    </div>

                </form>
            </Paper>
        </Grid>
        </Grid>
    );
}

export default RedirectPage;