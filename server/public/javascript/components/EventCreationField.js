const ce = React.createElement

class EventCreationField extends React.Component {
    constructor(props) {
        super(props)
        this.submit = props.submit
        this.state = {orgId:"", eventId:"", name: "", date: "", location: "", description: ""}/*title, date, time, numTickets, description (and then waiver options) */
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
    handleIdChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, orgId: val}});
    }
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
        this.setState((s) => {return {...s, time: val}});
    }
    handleDescriptionChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, description: val}});
    }
    handleSubmit(e) {
        this.submit({...this.state});
    }

    render() {
        return ce('div', {className: 'eventCreation_area'}, 
        ce('div', {className: 'labeled_field'}, 
                ce('[fix later] Event ID: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.title, onChange: (e) => this.handleIdChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('Event title: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.title, onChange: (e) => this.handleNameChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
            ce('Date: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.date, onChange: (e) => this.handleDateChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
            ce('Location: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.lime, onChange: (e) => this.handleLocationChange(e)}),
            ),ce('div', {className: 'labeled_field'}, 
            ce('# of Tickets: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.NumTickets, onChange: (e) => this.handleNumTicketsChange(e)}),
            ),ce('div', {className: 'labeled_field'}, 
            ce('Description: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.description, onChange: (e) => this.handleDescriptionChange(e)}),
            ),
            ce('button', {onClick: (e) => this.handleSubmit(e)}, "Submit"),
        )
    }
}

export default EventCreationField
