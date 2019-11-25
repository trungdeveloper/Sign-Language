import React, { Component } from 'react'
import callApi from '../../../utils/apiCaller'
import { ENDPOINTS } from '../../../constants/api'
import PostItem from '../../components/Post/ListPost/PostItem/PostItem'
import LoadingImage from '../../../assets/img/loading.gif'
import { ToastWarning } from '../../../utils/toastify'

export default class TBUPost extends Component {

    constructor(props) {
        super(props)
        this.state = {
            loading: true,
            selectedSubCategoryId: '',
            subcategories: [],
            tbuPosts: []
        }
    }

    componentDidMount = () => {
        this.fetchSubCategories()
        this.fetchPosts()
    }

    fetchPosts = async () => {
        try {
            this.setState({
                loading: true
            })
            let response = await callApi(`${ENDPOINTS.POST}?limit=50`, "GET", null);
            await this.getTBUPost(response.sources)
        } catch (e) {
            console.log(e);
        }
    }

    fetchSubCategories = async () => {
        try {
            this.setState({
                loading: true,
                subcategories: []
            })
            let response = await callApi(ENDPOINTS.SUBCATEGORY, "GET", null);
            this.setState({
                subcategories: response.sources
            })
        } catch (e) {
            console.log(e);
        }
    }

    getTBUPost = async (posts) => {
        if (posts.length > 0) {
            this.setState({
                tbuPosts: []
            })
            posts.forEach(async post => {
                try {
                    await fetch(post.video)
                } catch (e) {
                    let { tbuPosts } = this.state
                    tbuPosts.push(post)
                    this.setState({
                        tbuPosts
                    })
                }
            });
        } else {
            ToastWarning("Posts not found")
        }
        this.setState({
            loading: false
        })
        ToastWarning("We are still finding posts to be update. Please wait a few minutes before leave this page!")
    }

    onFilterBySubCategory = (e) => {
        let selectedSubCategoryId = e.target.value
        this.setState({ selectedSubCategoryId })
        this.fetchPostsBySubCategory(selectedSubCategoryId)
    }

    fetchPostsBySubCategory = async (selectedSubCategoryId) => {
        this.setState({
            loading: true
        })
        if (selectedSubCategoryId) {
            try {
                let response = await callApi(`${ENDPOINTS.SUBCATEGORY}/${selectedSubCategoryId}`, "GET", null);
                await this.getTBUPost(response.posts)
            } catch (e) {
                console.log(e);
            }
        } else {
            await this.fetchPosts()
        }
    }

    render() {
        const { loading, subcategories, tbuPosts } = this.state
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
                    {loading ? <div className="text-center">
                        <img width="100" src={LoadingImage} alt="loading" />
                        <br />
                        <p>Finding posts to be update. This action may take a few minutes. Please wait...</p>
                    </div> : (
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
                                        {tbuPosts.length ? (
                                            tbuPosts.map((post, index) => <PostItem key={index} index={index + 1} post={post} onRefresh={this.onFilterBySubCategory} />)
                                        ) : <tr><td colSpan="4"><h3>NO POST TO BE UPDATE FOUND</h3></td></tr>}
                                    </tbody>
                                </table>
                            </div>
                        )}
                </div>
            </div>
        )
    }
}
