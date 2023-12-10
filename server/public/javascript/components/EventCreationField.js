const ce = React.createElement

class EventCreationField extends React.Component {
    constructor(props) {
        super(props)
        this.submit = props.submit
        this.state = {orgId:-1, eventId:-1, name: "", date: "", location: "", description: "", image:""}/*title, date, time, numTickets, description (and then waiver options) */
        fetch('getUserId').then(res => res.json()).then(data => this.setState(s => {return {...s, orgId: data.userId}}))
    }
/*
    eventId: Option[Int],
    orgId: Int,
    name: String,
    date: java.sql.Timestamp,
    location: String,
    description: Option[String],
    image: Option[String]
  */
 /*
    handleIdChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, eventId: val}});
    }
    */
    handleNameChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, name: val}});
    }
    handleDateChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, date: val}});
    }
    handleLocationChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, location: val}});
    }
    handleDescriptionChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, description: val}});
    }
    handleImageChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, image: val}});
    }
    handleSubmit(e) {
        this.submit({...this.state, date: this.state.date + ":00"});
    }

    render() {
        return ce('div', {className: 'eventCreation_area'}, 
            /*
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field_create', type: 'text', placeholder:"EVENT ID", value: this.state.eventId, onChange: (e) => this.handleIdChange(e)}),
            ), */
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field_create', type: 'text', placeholder:"NAME OF EVENT", value: this.state.name, onChange: (e) => this.handleNameChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field_create', type: 'text', pattern: '[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}', title: 'yyyy-mm-dd hh:mm',placeholder:"DATE: yyyy-mm-dd hh:mm", value: this.state.date, onChange: (e) => this.handleDateChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field_create', type: 'text', placeholder:"LOCATION", value: this.state.location, onChange: (e) => this.handleLocationChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field_create', type: 'text', placeholder:"DESCRIPTION", value: this.state.description, onChange: (e) => this.handleDescriptionChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field_create', type: 'text', placeholder:"IMAGE", value: this.state.image, onChange: (e) => this.handleImageChange(e)}),
            ),
            ce('button', {className: 'login_button', onClick: (e) => this.handleSubmit(e)}, "SUBMIT"),
        );
    }
}

export default EventCreationField
