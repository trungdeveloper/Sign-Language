import React, { Component } from 'react'
import callApi from '../../../../utils/apiCaller'
import { ENDPOINTS } from '../../../../constants/api'
import { ToastError, ToastSuccess } from '../../../../utils/toastify'

export default class SubCategoryFormAdd extends Component {

	constructor(props) {
		super(props)
		this.state = {
			opened: false,
			categoryId: '',
			name: '',
			image: '',
			categories: []
		}
	}

	componentDidMount() {
		this.fetchCategories()
	}

	fetchCategories = async () => {
		try {
			let response = await callApi(ENDPOINTS.CATEGORY, "GET", null);
			this.setState({
				categories: response.sources
			})
		} catch (e) {
			console.log(e);
		}
	}

	openForm = () => {
		const { opened } = this.state
		this.setState({
			opened: !opened
		})
	}

	onChange = e => {
		this.setState({
			[e.target.name]: e.target.value
		});
	};

	onChangeFile = e => {
		let file = e.target.files[0]
		this.setState({ ...this.state, file })
	}

	uploadImage = async () => {
		const { file } = this.state;
		const formData = new FormData();
		formData.append('file', file)
		formData.append('folder', 'subcategories');
		return await callApi(ENDPOINTS.UPLOAD, 'POST', formData)
	}

	onSave = async e => {
		e.preventDefault()
		let image = await this.uploadImage()
		let { categoryId, name, categories } = this.state
		if (!categoryId) {
			categoryId = categories[0].id
		}
		let data = {
			categoryId,
			name,
			image
		}
		try {
			await callApi(ENDPOINTS.SUBCATEGORY, "POST", data)
			ToastSuccess('Thêm thành công!')
			let { opened } = this.state
			this.setState({
				opened: !opened,
				categoryId: '',
				name: '',
				image: '',
			})
		} catch (e) {
			ToastError(e.response.data)
		}
	}

	render() {
		const { opened, name, categories } = this.state
		return (
			<div className="panel panel-primary">
				<div className="panel-heading">
					<button onClick={this.openForm} className="btn btn-default">ADD</button>
				</div>
				{opened ? <div className="panel-body">
					<form>

						<div className="form-group">
							<label>Subcategory Name</label>
							<input type="text" className="form-control" placeholder="Subcategory Name" name="name" defaultValue={name} onChange={this.onChange} required />
						</div>

						<label>Category</label>
						<select name="categoryId" className="form-control" required="required" onChange={this.onChange} >
							{categories.map((category, index) => <option key={index} value={category.id}>{category.name}</option>)}
						</select>

						<label>Image</label>
						<input type="file" accept="image/*" className="form-control" onChange={this.onChangeFile} />

						<button onClick={this.onSave} className="btn btn-primary mt-15">SUBMIT</button>

					</form>
				</div> : ''}
			</div>
		)
	}
}