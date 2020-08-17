import React from 'react';
import { withRouter } from "react-router-dom";
import {useStore} from '../../hooks-store/store';
import {LOGOUT_ORGANIZATION} from '../../hooks-store/actionTypes';

import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
}));

const ButtonAppBar = (props) => {
  const classes = useStyles();
  const [state, dispatch] = useStore();

  const renderAuthComponents = () => {
    return(
      <div>
        
      </div>
    )
  };

  return (
    <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6" 
                            className={classes.title}
                            onClick={() => props.history.push("/")}>
                    Url shortener
                </Typography>

                <Button className={classes.menuButton} 
                        color="secondary" 
                        variant="contained"
                        onClick={() => {props.history.push("/redirectPage")}}>
                            Redirect me
                </Button>

                <Button className={classes.menuButton} 
                        color="secondary" 
                        variant="contained"
                        onClick={() => {state.isAuthenticated ? 
                                        dispatch(LOGOUT_ORGANIZATION) : 
                                        props.history.push("/auth/login")}}>
                            {state.isAuthenticated ? "Logout" : "Login"}
                </Button>
            </Toolbar>
        </AppBar>
    </div>
  );
}

export default withRouter(ButtonAppBar);