import React, {useState} from 'react';
import './App.css';

import axios from 'axios'

function App() {

  const [slug, setSlug] = useState("");
  const [url, setUrl] = useState("");

  const handleSubmit = event => {
    event.preventDefault();

    console.log(slug);
    console.log(url);

    let obj = {
      "slug": slug,
      "url": url
    }

    axios.post('http://localhost:8081/urls', obj)
      .then((response) => {
        console.log(response.data);
      });
  }

  const onSlugChangeHandler = (event) => {
    setSlug(event.target.value);
  }

  const onUrlChangeHandler = (event) => {
    setUrl(event.target.value);
  }

  return (
    <div className="App">

      <h1>Hello</h1>

      <form onSubmit={handleSubmit}>
        <label>
          Slug:
          <input type="text" value={slug} onChange={onSlugChangeHandler} />
        </label>
        <br/>
        <label>
          Url:
          <input type="text" value={url} onChange={onUrlChangeHandler} />
        </label>
        <br/>
        <input type="submit" value="Submit" />
      </form>

    </div>
  );
}

export default App;
