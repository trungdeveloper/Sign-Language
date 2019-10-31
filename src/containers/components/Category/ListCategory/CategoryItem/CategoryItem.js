import React, { Component } from 'react'
import './CategoryItem.css'
import { confirmDelete } from '../../../../../utils/confirmer'
import callApi from '../../../../../utils/apiCaller'
import { ENDPOINTS } from '../../../../../constants/api'

export default class CategoryItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: '',
            name: '',
            description: '',
            editing: false
        };
    }

    componentDidMount() {
        let { id, name, description } = this.props.category;
        this.setState({
            id,
            name,
            description
        })
    }

    // componentWillReceiveProps(nextProps) {
    //     let { id, name, description } = nextProps.category;
    //     this.setState({
    //         id,
    //         name,
    //         description
    //     })
    // }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    onEdit = () => {
        this.setState({
            editing: !this.state.editing
        });
    }

    onUpdate = () => {
        this.setState({
            editing: false
        });
        let { id, name, description } = this.state;
        if (this.props.category.name !== name || this.props.category.description !== description) {
            this.props.onUpdateCategory({ id, name, description });
        }
    }

    onCancelUpdate = () => {
        this.setState({
            editing: false
        });
        let { name, description } = this.props.category;
        if (name !== this.state.name || description !== this.state.description) {
            this.setState({
                name,
                description
            });
        }
    }

    onDelete = (id) => {
        confirmDelete().then((result) => {
            if (result.value) {
                try {
                    let response = callApi(`${ENDPOINTS.CATEGPRY}/${id}`, "DELETE", null)
                    console.log(response);
                } catch (e) {
                    console.log(e);
                }
            }
        })
    }

    render() {
        const { id, name, description, editing } = this.state;
        return (
            <tr>
                <td>{this.props.index}</td>
                <td>
                    <input type="text" name="name" className={editing ? 'editing' : 'border-none'} onChange={this.onChange} value={name || ''} readOnly={editing ? false : true} />
                </td>
                <td>
                    <input type="text" name="description" className={editing ? 'editing' : 'border-none'} onChange={this.onChange} value={description || ''} readOnly={editing ? false : true} />
                </td>
                <td className="text-center">
                    {editing ?
                        <span>
                            <button color="success" className="mr-10" onClick={this.onUpdate}><i className="fa fa-floppy-o"></i></button>
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