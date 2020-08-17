import React, {useEffect} from 'react';
import {Route, Switch} from 'react-router-dom'; 

import {useStore} from './hooks-store/store';
import {SET_CURRENT_ORGANIZATION} from './hooks-store/actionTypes';
import parseJwt from './jwtParser/jwtParser';
import {setAuthToken} from './setAuthToken';

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

  const [state, dispatch] = useStore();

  useEffect(() => {
    let token = localStorage.getItem("jwtToken");
    if(token) {   
      setAuthToken(token);   
      const decoded = parseJwt(token);
      dispatch(SET_CURRENT_ORGANIZATION, decoded);

      const currentTime = Date.now() / 1000;
      if(decoded.exp < currentTime) {
        //logout
      }
    }
  }, []);

  const renderUnAuthPage = () => {
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

  const renderAuthPage = () => {
    return(
      <Switch>
        <Route path="/landingPage" component={LandingPage}></Route>
        <Route path="/redirectPage" component={RedirectPage}></Route>
        <Route path="/" component={LandingPage}></Route>
      </Switch>
    ) 
  }

  return (
    <div className={classes.root}>
      <Navbar/>
      {state.isAuthenticated ? renderAuthPage() : renderUnAuthPage()}
    </div>
  );
}

export default App;
