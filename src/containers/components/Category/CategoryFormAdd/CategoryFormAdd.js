import React, { Component } from 'react'
import callApi from '../../../../utils/apiCaller'
import { ENDPOINTS } from '../../../../constants/api'

export default class CategoryFormAdd extends Component {

	constructor(props) {
		super(props)
		this.state = {
			opened: false,
			name: '',
			description: ''
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

	onSave = e => {
		e.preventDefault()
		let { name, description } = this.state
		let data = {
			name,
			description
		}
		try {
			let response = callApi(ENDPOINTS.CATEGORY, "POST", data)
			console.log(response);
		} catch (e) {
			console.log(e);
		}
	}

	render() {
		const { opened, name, description } = this.state
		return (
			<div className="panel panel-primary">
				<div className="panel-heading">
					<button onClick={this.openForm} className="btn btn-default">ADD</button>
				</div>
				{opened ? <div className="panel-body">
					<form>

						<div className="form-group">
							<label>Category Name</label>
							<input type="text" className="form-control" placeholder="Category Name" name="name" defaultValue={name} onChange={this.onChange} />
						</div>

						<label>Description</label>
						<textarea className="form-control" rows="3" placeholder="Enter category description here" name="description" defaultValue={description} onChange={this.onChange} ></textarea>

						<button onClick={this.onSave} className="btn btn-primary mt-15">SUBMIT</button>

					</form>
				</div> : ''}
			</div>
		)
	}
}