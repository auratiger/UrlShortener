import React, {useState} from 'react';

import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';

import axios from 'axios';
import parseJwt from '../../jwtParser/jwtParser';

const useStyles = makeStyles((theme) => ({

    paper: {
      padding: theme.spacing(4),
      textAlign: 'center',
      color: theme.palette.text.secondary,
    },
}));

const RedirectPage = () => {

    const classes = useStyles();
    const [slug, setSlug] = useState("");

    const onSlugChangeHandler = (event) => {
        setSlug(event.target.value)
    }

    const handleSubmit = () => {

        if(slug === null || slug.trim().length < 1){
            return;
        }

        axios.get(`http://localhost:8081/testJwt/jboxers/pass123`)
            .then(response => {
                console.log(response.data);
                let a = parseJwt(response.data);
                console.log(JSON.parse(a.sub));
                // window.location.replace(response.data);
            }).catch(err => {
                console.log(err);
            })

        // axios.get('http://localhost:8081/urls/'+slug)
        //     .then(response => {
        //         console.log(response.data);
        //     })
        //     .catch(err => {
        //         console.log(err);
        //     })
    }

    return(
        <Grid container>
        <Grid item xs={12}>
            <h1 className={classes.paper}></h1>
        </Grid>
        <Grid item xs={4}></Grid>
        <Grid item xs={4}>
            <Paper className={classes.paper} elevation={3}>
                <h3>Redirect me!</h3>
                <form>
                    <div>
                        <TextField label={"Slug"} value={slug} onChange={onSlugChangeHandler} />
                    </div>
                    <br></br>
                    <Button onClick={handleSubmit} variant="contained">Submit</Button>
                </form>
            </Paper>
        </Grid>
        </Grid>
    );
}

export default RedirectPage;