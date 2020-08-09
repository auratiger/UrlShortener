import React from 'react';
import {Route, Switch} from 'react-router-dom'; 

import './App.css';
import { makeStyles } from '@material-ui/core/styles';

import Navbar from './Components/Navbar/Navbar';
import LandingPage from './Components/LandingPage/LandingPage';
import SignUp from './Components/Auth/SignUp/SignUp';
import Login from './Components/Auth/Login/Login';
import RedirectPage from './Components/RedirectPage/RedirectPage';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1
  },
}));

const App = (props) => {
  const classes = useStyles();

  const renderPage = () => {
    return(
      <Switch>
        <Route path="/auth/login" component={Login}></Route>
        <Route path="/auth/signup" component={SignUp}></Route>
        <Route path="/landingPage" component={LandingPage}></Route>
        <Route path="/redirectPage" component={RedirectPage}></Route>
        <Route path="/" component={LandingPage}></Route>
      </Switch>
    )
  }

  return (
    <div className={classes.root}>
      <Navbar/>
      {renderPage()}
    </div>
  );
}

export default App;
