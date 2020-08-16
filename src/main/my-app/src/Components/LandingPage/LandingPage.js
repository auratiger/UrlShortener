import React, {useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
import Snackbar from '@material-ui/core/Snackbar';
import MuiAlert from '@material-ui/lab/Alert';

import axios from 'axios'
// import { useStore } from '../../hooks-store/store';

const useStyles = makeStyles((theme) => ({

  paper: {
    padding: theme.spacing(4),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  },
}));

const LandingPage = (props) => {

  const classes = useStyles();

  // const [state, dispatch] = useStore();

  const [slug, setSlug] = useState("");
  const [url, setUrl] = useState("");
  const [open, setOpen] = useState(false);
  const [response, setResponse] = useState("")
  const [responseError, setResponseError] = useState(null);

  // useEffect(() => {
  //   document.body.style.backgroundColor = "#262626";

  //   return () => {
  //     document.body.style.backgroundColor = '#fff'
  //   }
  // })

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

    setResponseError(null);

    axios.post('http://localhost:8081/urls', obj)
      .then((response) => {
        // console.log(response.data);

        let text = `Slug : ${response.data.slug} \nUrl : ${response.data.url}`;

        setResponse(text);
      }).catch((err) => {
        console.log(err);
        setResponseError(err.response);
        setResponse(err.response.data);
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
    <Grid container>
    <Grid item xs={12}>
        <h1 className={classes.paper}></h1>
    </Grid>
    <Grid item xs={4}></Grid>
    <Grid item xs={4}>
        <Paper className={classes.paper} elevation={3}>
            <h3>Create new shortend url</h3>
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
        <MuiAlert style={{whiteSpace: "pre-wrap"}} 
                    elevation={6} 
                    variant="filled"
                    onClose={handleClose}
                    severity={responseError === null ? "success" : "error"}>
                {response}
        </MuiAlert>
    </Snackbar>
    </Grid>
  );
}

export default LandingPage;
