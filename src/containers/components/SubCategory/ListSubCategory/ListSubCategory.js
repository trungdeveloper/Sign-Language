import React, { Component } from 'react'
import callApi from '../../../../utils/apiCaller'
import { ENDPOINTS } from '../../../../constants/api'
import SubCategoryItem from './SubCategoryItem/SubCategoryItem'
import LoadingImage from '../../../../assets/img/loading.gif'

export default class ListSubCategory extends Component {

    constructor(props) {
        super(props)
        this.state = {
            loading: true,
            subcategories: []
        }
    }

    componentDidMount() {
        this.fetchSubCategories()
    }

    fetchSubCategories = async () => {
        try {
            let response = await callApi(ENDPOINTS.SUBCATEGORY, "GET", null);
            this.setState({
                loading: false,
                subcategories: response.sources
            })
        } catch (e) {
            console.log(e);
        }
    }

    render() {
        const { loading, subcategories } = this.state
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
                                        <th>IMAGE</th>
                                        <th>ACTIONS</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {subcategories.length ? (
                                        subcategories.map((subcategory, index) => <SubCategoryItem key={index} index={index + 1} subcategory={subcategory} onRefresh={this.fetchSubCategories} />)
                                    ) : <tr><td colSpan="4"><h3>NO SUBCATEGORY FOUND</h3></td></tr>}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            </div>
        )
    }
}
