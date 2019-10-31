import React, { Component } from 'react'
import './SubCategoryItem.css'
import { confirmDelete } from '../../../../../utils/confirmer'
import callApi from '../../../../../utils/apiCaller'
import { ENDPOINTS } from '../../../../../constants/api'
import { ToastSuccess, ToastError } from '../../../../../utils/toastify'

export default class SubCategoryItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            categoryId: '',
            id: '',
            name: '',
            image: '',
            file: null,
            editing: false
        };
    }

    componentDidMount() {
        let { categoryId, id, name, image } = this.props.subcategory;
        this.setState({
            categoryId,
            id,
            name,
            image
        })
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

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

    onEdit = () => {
        this.setState({
            editing: !this.state.editing
        });
    }

    onSaveUpdate = async e => {
        this.setState({
            editing: false
        });
        let { categoryId, id, name, image, file } = this.state;
        if (this.props.subcategory.name !== name || this.props.subcategory.image !== image || file) {
            let image = await this.uploadImage()
            let data = {
                categoryId,
                name,
                image
            }
            try {
                await callApi(`${ENDPOINTS.SUBCATEGORY}/${id}`, "PUT", data)
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
        let { name, image } = this.props.subcategory;
        if (name !== this.state.name || image !== this.state.image) {
            this.setState({
                name,
                image
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
            await callApi(`${ENDPOINTS.SUBCATEGORY}/${id}`, "DELETE", null)
            ToastSuccess('Xóa thành công!')
            this.props.onRefresh()
        } catch (e) {
            console.log(e);
            ToastError(e)
        }
    }

    render() {
        const { id, name, image, editing } = this.state;
        return (
            <tr>
                <td>{this.props.index}</td>
                <td>
                    <input type="text" name="name" className={editing ? 'editing' : 'border-none'} onChange={this.onChange} value={name || ''} readOnly={editing ? false : true} />
                </td>
                <td>
                    {(image && !editing) ? <img width="200" src={image} alt={name} /> : <input type="file" accept="image/*" className={editing ? 'editing' : 'border-none'} onChange={this.onChangeFile} disabled={editing ? false : true} />}
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