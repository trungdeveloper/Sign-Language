import React, { Component } from 'react'
import './PostItem.css'
import { confirmDelete } from '../../../../../utils/confirmer'
import callApi from '../../../../../utils/apiCaller'
import { ENDPOINTS } from '../../../../../constants/api'
import { ToastSuccess, ToastError, ToastWarning } from '../../../../../utils/toastify'

export default class PostItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            subCategoryId: '',
            id: '',
            keyword: '',
            image: '',
            video: '',
            imageFile: null,
            videoFile: null,
            editing: false
        };
    }

    componentDidMount() {
        let { subCategoryId, id, keyword, image, video } = this.props.post;
        this.setState({
            subCategoryId,
            id,
            keyword,
            image,
            video
        })
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onChangeImage = e => {
        let imageFile = e.target.files[0]
        this.setState({ imageFile })
    }

    onChangeVideo = e => {
        let videoFile = e.target.files[0]
        this.setState({ videoFile })
    }

    uploadImage = async () => {
		console.log("Uploading image");
		const { imageFile } = this.state;
		const formData = new FormData();
		formData.append('file', imageFile)
		formData.append('folder', 'posts');
		return await callApi(ENDPOINTS.UPLOAD, 'POST', formData)
	}

	uploadVideo = async () => {
		console.log("Uploading video");
		const { videoFile } = this.state;
		const formData = new FormData();
		formData.append('file', videoFile)
		formData.append('folder', 'posts');
		return await callApi(ENDPOINTS.UPLOAD, 'POST', formData)
	}

    onEdit = () => {
        this.setState({
            editing: !this.state.editing
        });
    }

    onSaveUpdate = async e => {
        this.setState({
            editing: false
        });
        let { subCategoryId, id, keyword, image, video, imageFile, videoFile } = this.state;
        if (this.props.post.keyword !== keyword || imageFile || videoFile) {
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
                await callApi(`${ENDPOINTS.POST}/${id}`, "PUT", data)
                ToastSuccess('Cập nhật thành công!')
                this.props.onRefresh()
            } catch (e) {
                ToastError(e.response.data)
            }
        }
    }

    onCancelUpdate = () => {
        this.setState({
            editing: false
        });
        let { keyword, imageFile, videoFile } = this.props.post;
        if (keyword !== this.state.name || imageFile || videoFile) {
            this.setState({
                keyword
            });
        }
    }

    onDelete = id => {
        confirmDelete().then((result) => {
            if (result.value) {
                this.delete(id)
            }
        })
    }

    delete = async id => {
        try {
            await callApi(`${ENDPOINTS.POST}/${id}`, "DELETE", null)
            ToastSuccess('Xóa thành công!')
            this.props.onRefresh()
        } catch (e) {
            console.log(e);
            ToastError(e)
        }
    }

    render() {
        const { id, keyword, image, video, editing } = this.state;
        return (
            <tr>
                <td>{this.props.index}</td>
                <td>
                    <input type="text" name="keyword" className={editing ? 'editing' : 'border-none'} onChange={this.onChange} value={keyword || ''} readOnly={editing ? false : true} />
                </td>
                <td>
                    {(image && !editing) ? <img width="310" src={image} alt={keyword} /> : <input type="file" accept="image/*" className={editing ? 'editing' : 'border-none'} onChange={this.onChangeImage} disabled={editing ? false : true} />}
                </td>
                <td>
                    {(video && !editing) ?
                        <video width="400" controls>
                            <source src={video} type="video/mp4" />
                        </video>
                        : <input type="file" accept="video/*" className={editing ? 'editing' : 'border-none'} onChange={this.onChangeVideo} disabled={editing ? false : true} />}
                </td>
                <td className="text-center">
                    {editing ?
                        <span>
                            <button color="success" className="mr-10" onClick={this.onSaveUpdate}><i className="fa fa-floppy-o"></i></button>
                            <button color="secondary" className="mr-10" onClick={this.onCancelUpdate}><i className="fa fa-ban"></i></button>
                        </span> :
                        <button color="warning" className="mr-10" onClick={this.onEdit}><i className="fa fa-pencil"></i></button>
                    }
                    <button color="danger" onClick={() => this.onDelete(id)}><i className="fa fa-trash-o"></i></button> &nbsp;
                </td>
            </tr>
        )
    }
}