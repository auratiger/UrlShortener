import React, {useState} from 'react';
import './App.css';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

import axios from 'axios'

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  },
}));

function Alert(props){

  let text = `Slug: ${props.response.slug} \n
   Url: ${props.response.url}` 

  return (
    <MuiAlert elevation={6} variant="filled" {...props}>
      {text}
    </MuiAlert>
  )
}

function App() {

  const classes = useStyles();

  const [slug, setSlug] = useState("");
  const [url, setUrl] = useState("");
  const [open, setOpen] = useState(false);
  const [response, setResponse] = useState({})

  const handleSubmit = event => {
    event.preventDefault();

    if(url.length < 1){
      console.log("url is empty");
      return;
    }

    let obj = {
      "slug": slug,
      "url": url
    }

    axios.post('http://localhost:8081/urls', obj)
      .then((response) => {
        // console.log(response.data);
        setResponse(response.data);
      });
      
    setOpen(true);
    setSlug("")
    setUrl("")
  }

  const onSlugChangeHandler = (event) => {
    setSlug(event.target.value);
  }

  const onUrlChangeHandler = (event) => {
    setUrl(event.target.value);
  }
  
  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }

    setOpen(false);
  };

  return (
    <div className={classes.root}>
      <Grid container spacing={3}>
        <Grid item xs={12}>
          <h1 className={classes.paper}>Url shortener</h1>
        </Grid>
        <Grid item xs={4}></Grid>
        <Grid item xs={4}>
          <Paper className={classes.paper}>
              <form>
                <div>
                  <TextField label={"Slug"} value={slug} onChange={onSlugChangeHandler} />
                </div>
                <div>
                  <TextField label={"Url"} value={url} onChange={onUrlChangeHandler} />
                </div>
                <br/>
                <Button onClick={handleSubmit} variant="contained">Submit</Button>
              </form>
          </Paper>
        </Grid>
        <Snackbar
          anchorOrigin={{vertical: "bottom", horizontal: "center"}}
          open={open}
          onClose={handleClose}>
            <Alert onClose={handleClose} severity="success" response={response}/>
        </Snackbar>
      </Grid>
    </div>
  );
}

export default App;
