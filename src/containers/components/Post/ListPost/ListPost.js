import React, { Component } from 'react'
import callApi from '../../../../utils/apiCaller'
import { ENDPOINTS } from '../../../../constants/api'
import PostItem from './PostItem/PostItem'
import LoadingImage from '../../../../assets/img/loading.gif'
import { ToastError } from '../../../../utils/toastify'

export default class ListPost extends Component {

    constructor(props) {
        super(props)
        this.state = {
            loading: true,
            selectedSubCategoryId: '',
            subcategories: [],
            posts: []
        }
    }

    componentDidMount() {
        this.fetchPosts()
        this.fetchSubCategories()
    }

    fetchSubCategories = async () => {
        try {
            this.setState({
                loading: true,
                subcategories: []
            })
            let response = await callApi(ENDPOINTS.SUBCATEGORY, "GET", null);
            this.setState({
                loading: false,
                subcategories: response.sources
            })
        } catch (e) {
            ToastError(e.response.message)
        }
    }

    fetchPosts = async () => {
        try {
            this.setState({
                loading: true,
                posts: []
            })
            let response = await callApi(`${ENDPOINTS.POST}?limit=50`, "GET", null);
            this.setState({
                loading: false,
                posts: response.sources
            })
        } catch (e) {
            ToastError(e.response.message)
        }
    }

    onFilterBySubCategory = (e) => {
        let selectedSubCategoryId = e.target.value
        this.setState({ selectedSubCategoryId })
        this.fetchPostsBySubCategory(selectedSubCategoryId)
    }

    fetchPostsBySubCategory = async (selectedSubCategoryId) => {
        if (selectedSubCategoryId) {
            this.setState({
                loading: true,
                posts: []
            })
            try {
                let response = await callApi(`${ENDPOINTS.SUBCATEGORY}/${selectedSubCategoryId}`, "GET", null);
                this.setState({
                    loading: false,
                    posts: response.posts
                })
            } catch (e) {
                ToastError(e.response.message)
            }
        } else {
            this.setState({
                loading: true,
                posts: []
            })
            try {
                let response = await callApi(ENDPOINTS.POST, "GET", null);
                this.setState({
                    loading: false,
                    posts: response.sources
                })
            } catch (e) {
                ToastError(e.response.message)
            }
        }
    }

    render() {
        const { loading, subcategories, posts } = this.state
        return (
            <div className="panel panel-primary">
                <div className="panel-heading">
                    <h3 className="panel-title">POSTS</h3>
                </div>
                <div className="panel-body">
                    <div className="form-group">
                        <label htmlFor="subcategory">SELECT SUBCATEGORY:</label>
                        <select className="form-control" id="subcategory" onChange={this.onFilterBySubCategory}>
                            <option value="">All</option>
                            {subcategories.map(subcategory => <option key={subcategory.id} value={subcategory.id}>{subcategory.name}</option>)}
                        </select>
                    </div>
                    {loading ? <div className="text-center"><img width="100" src={LoadingImage} alt="loading" /></div> : (
                        <div className="table-responsive">
                            <table className="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>KEYWORD</th>
                                        <th>IMAGE</th>
                                        <th>VIDEO</th>
                                        <th>ACTIONS</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {posts.length ? (
                                        posts.map((post, index) => <PostItem key={index} index={index + 1} post={post} onRefresh={this.onFilterBySubCategory} />)
                                    ) : <tr><td colSpan="4"><h3>NO POST FOUND</h3></td></tr>}
                                </tbody>
                            </table>
                        </div>
                    )}
                </div>
            </div>
        )
    }
}
