import React, { Component } from 'react'
import SubCategoryFormAdd from '../../components/SubCategory/SubCategoryFormAdd/SubCategoryFormAdd'
import ListSubCategory from '../../components/SubCategory/ListSubCategory/ListSubCategory'

export default class SubCategory extends Component {
    render() {
        return (
            <>
                <SubCategoryFormAdd />
                <ListSubCategory />
            </>
        )
    }
}