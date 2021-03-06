import React, {useEffect} from 'react';
import {Route, Switch} from 'react-router-dom'; 

import {useStore} from './hooks-store/store';
import {SET_CURRENT_ORGANIZATION, LOGOUT_ORGANIZATION} from './hooks-store/actionTypes';
import parseJwt from './jwtParser/jwtParser';
import {setAuthToken} from './setAuthToken';

import './App.css';
import { makeStyles } from '@material-ui/core/styles';

import Navbar from './Components/Navbar/Navbar';
import LandingPage from './Components/LandingPage/LandingPage';
import SignUp from './Components/Auth/SignUp/SignUp';
import Login from './Components/Auth/Login/Login';
import RedirectPage from './Components/RedirectPage/RedirectPage';
import ProfilePage from './Components/ProfilePage/ProfilePage';
import RedirectionList from './Components/RedirectionList/RedirectionList';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    height: "100vh"
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
        dispatch(LOGOUT_ORGANIZATION);
      }
    }
  }, []);

  const renderUnAuthPage = () => {
    return(
      <Switch>
        <Route path="/auth/login" component={Login}></Route>
        <Route path="/auth/signup" component={SignUp}></Route>
        <Route path="/landingPage" component={LandingPage}></Route>
        <Route path="/redirectPage/list" component={RedirectionList}></Route>
        <Route path="/redirectPage" component={RedirectPage}></Route>
        <Route path="/" component={LandingPage}></Route>
      </Switch>
    )
  }

  const renderAuthPage = () => {
    return(
      <Switch>
        <Route path="/landingPage" component={LandingPage}></Route>
        <Route path="/organization/profile" component={ProfilePage}></Route>
        <Route path="/redirectPage/organization/list" render={(props) => (
                <RedirectionList {...props} organizationTab={true} />)}></Route>
        <Route path="/redirectPage/list" component={RedirectionList}></Route>
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
