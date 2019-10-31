import React, { Component } from 'react'
import ListPost from '../../components/Post/ListPost/ListPost'
import PostFormAdd from '../../components/Post/PostFormAdd/PostFormAdd'

export default class Post extends Component {
    render() {
        return (
            <>
                <PostFormAdd />
                <ListPost />
            </>
        )
    }
}