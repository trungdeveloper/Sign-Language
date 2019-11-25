import React, { Component } from 'react'

export default class Header extends Component {
    render() {
        return (
            <nav className="navbar navbar-inverse">
                <div className="container-fluid">
                    <div className="navbar-header">
                        <button type="button" className="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                            <span className="icon-bar"></span>
                        </button>
                        <a className="navbar-brand" href="/">SIGN LANGUAGE</a>
                    </div>
                    <div className="collapse navbar-collapse" id="myNavbar">
                        <ul className="nav navbar-nav">
                            <li><a href="/">CATEGORY</a></li>
                            <li><a href="/subcategories">SUBCATEGORY</a></li>
                            <li><a href="/posts">POST</a></li>
                            <li><a href="/tbuposts">TO BE UPDATE POST</a></li>
                        </ul>
                    </div>
                </div>
            </nav>
        )
    }
}