import React, { Component } from 'react'
import ListCategory from '../../components/Category/ListCategory/ListCategory'
import CategoryFormAdd from '../../components/Category/CategoryFormAdd/CategoryFormAdd'

export default class Category extends Component {
    render() {
        return (
            <>
                <CategoryFormAdd />
                <ListCategory />
            </>
        )
    }
}