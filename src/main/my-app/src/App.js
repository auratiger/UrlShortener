import React, {useState} from 'react';
import './App.css';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';

import axios from 'axios'

const useStyles = makeStyles({
  root: {
    width: 250,
    height: 180,
    display: "inline-block"
  },
  result: {
    width: 250,
    height: 180,
    display: "inline-block"
  },
});

function App() {

  const classes = useStyles();

  const [slug, setSlug] = useState("");
  const [url, setUrl] = useState("");

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
        console.log(response.data);
      });
      
    setSlug(""); 
    setUrl("");
  }

  const onSlugChangeHandler = (event) => {
    setSlug(event.target.value);
  }

  const onUrlChangeHandler = (event) => {
    setUrl(event.target.value);
  }

  return (
    <div className="App">

      <h1>Url shortener</h1>

      <Paper className={classes.root}>
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

    </div>
  );
}

export default App;
