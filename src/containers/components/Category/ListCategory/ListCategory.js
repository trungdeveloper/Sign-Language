import React, { Component } from 'react'
import callApi from '../../../../utils/apiCaller'
import CategoryItem from './CategoryItem/CategoryItem'
import LoadingImage from '../../../../assets/img/loading.gif'

export default class ListCategory extends Component {

    constructor(props) {
        super(props)
        this.state = {
            loading: true,
            categories: []
        }
    }

    componentDidMount() {
        this.fetchCategories()
    }

    fetchCategories = async () => {
        try {
            let response = await callApi("categories", "GET", null);
            this.setState({
                loading: false,
                categories: response.sources
            })
        } catch (e) {
            console.log(e);
        }
    }

    render() {
        const { loading, categories } = this.state
        return (
            <div className="panel panel-primary">
                <div className="panel-heading">
                    <h3 className="panel-title">Categories</h3>
                </div>
                <div className="panel-body">
                    {loading ? <div className="text-center"><img width="100" src={LoadingImage} alt="loading" /></div> : (
                        <div className="table-responsive">
                            <table className="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>NAME</th>
                                        <th>DESCRIPTION</th>
                                        <th>ACTIONS</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {categories.length ? (
                                        categories.map((category, index) => <CategoryItem key={index} index={index + 1} category={category} />)
                                    ) : <tr><td colSpan="4" className="text-center"><h3>NO CATEGORY FOUND</h3></td></tr>}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            </div>
        )
    }
}
