import React from 'react';
import Login from './login/login';
import App from './index/App';
import {Router,Route, Switch, Redirect} from "react-router-dom";
import history from "./history";
function AppRoute(){
    
    return (
        <Router history={history} >
            <Switch>
                <Route path="/app" component={App} />
                <Route path="/" component={Login} />
            </Switch>
            <Redirect to="/" />
        </Router>
    );
}

export default AppRoute;