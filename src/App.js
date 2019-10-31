import React from 'react'
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Category from './containers/pages/Category/Category';
import SubCategory from './containers/pages/SubCategory/SubCategory';
import Post from './containers/pages/Post/Post';
import { ToastContainer } from 'react-toastify';
import "react-toastify/dist/ReactToastify.css";
import Header from './containers/components/Header/Header';

function App() {
  return (
    <div className="container-fluid">
      <Header />
      <ToastContainer />
      <Router>
        <Switch>
          <Route exact path="/" name="Category Page" component={Category} />
          <Route exact path="/posts" name="Post Page" component={Post} />
          <Route exact path="/subcategories" name="SubCategory Page" component={SubCategory} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;