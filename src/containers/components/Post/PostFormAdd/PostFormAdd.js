import React, { Component } from 'react'
import callApi from '../../../../utils/apiCaller'
import { ENDPOINTS } from '../../../../constants/api'
import { ToastError, ToastSuccess, ToastWarning } from '../../../../utils/toastify'

export default class PostFormAdd extends Component {

	constructor(props) {
		super(props)
		this.state = {
			opened: false,
			subCategoryId: '',
			keyword: '',
			image: '',
			video: '',
			imageFile: null,
			videoFile: null,
			subCategories: []
		}
	}

	componentDidMount() {
		this.fetchSubCategories()
	}

	fetchSubCategories = async () => {
		try {
			let response = await callApi(ENDPOINTS.SUBCATEGORY, "GET", null);
			this.setState({
				subCategories: response.sources
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

	onChangeImage = e => {
		let imageFile = e.target.files[0]
		this.setState({
			imageFile
		})
	}

	onChangeVideo = e => {
		let videoFile = e.target.files[0]
		this.setState({
			videoFile
		})
	}

	uploadImage = async () => {
		const { imageFile } = this.state;
		const formData = new FormData();
		formData.append('file', imageFile)
		formData.append('folder', 'posts');
		return await callApi(ENDPOINTS.UPLOAD, 'POST', formData)
	}

	uploadVideo = async () => {
		const { videoFile } = this.state;
		const formData = new FormData();
		formData.append('file', videoFile)
		formData.append('folder', 'posts');
		return await callApi(ENDPOINTS.UPLOAD, 'POST', formData)
	}

	onSave = async e => {
		e.preventDefault()
		let { subCategoryId, keyword, image, video, imageFile, videoFile, subCategories } = this.state
		if (!subCategoryId) {
			subCategoryId = subCategories[0].id
		}
		if (imageFile) {
			ToastWarning("Đang tải lên hình ảnh, vui lòng đợi trong giây lát!")
			image = await this.uploadImage()
		}
		if (videoFile) {
			ToastWarning("Đang tải lên video, vui lòng đợi trong giây lát!")
			video = await this.uploadVideo()
		}
		let data = {
			subCategoryId,
			keyword,
			image,
			video
		}
		console.log(data);
		try {
			await callApi(ENDPOINTS.POST, "POST", data)
			ToastSuccess('Thêm thành công!')
			let { opened } = this.state
			this.setState({
				opened: !opened,
				subCategoryId: '',
				keyword: '',
				image: '',
				video: '',
				imageFile: null,
				videoFile: null
			})
		} catch (e) {
			ToastError(e.response.data)
		}
	}

	render() {
		const { opened, keyword, subCategories } = this.state
		return (
			<div className="panel panel-primary">
				<div className="panel-heading">
					<button onClick={this.openForm} className="btn btn-default">ADD</button>
				</div>
				{opened ? <div className="panel-body">
					<form>

						<div class="row">

							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div className="form-group">
									<label>Keyword</label>
									<input type="text" className="form-control" placeholder="Keyword" name="keyword" defaultValue={keyword} onChange={this.onChange} required />
								</div>
							</div>

							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<label>Subcategory</label>
								<select name="subCategoryId" className="form-control" required="required" onChange={this.onChange} >
									{subCategories.map((subCategory, index) => <option key={index} value={subCategory.id}>{subCategory.name}</option>)}
								</select>
							</div>

							<div class="clearfix"></div>

							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div className="form-group">
									<label>Image</label>
									<input type="file" accept="image/*" className="form-control" onChange={this.onChangeImage} />
								</div>
							</div>

							<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
								<div className="form-group">
									<label>Video</label>
									<input type="file" accept="video/*" className="form-control" onChange={this.onChangeVideo} />
								</div>
							</div>

							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<button onClick={this.onSave} className="btn btn-primary pull-right">SUBMIT</button>
							</div>

						</div>

					</form>
				</div> : ''}
			</div>
		)
	}
}