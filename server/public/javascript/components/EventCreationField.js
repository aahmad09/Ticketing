const ce = React.createElement

class EventCreationField extends React.Component {
    constructor(props) {
        super(props)
        this.submit = props.submit
        this.state = {Title: "", Date: "", Time: "", NumTickets: "", Description: ""}/*title, date, time, numTickets, description (and then waiver options) */
    }

    handleTitleChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, title: val}});
    }
    handlePasswordChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, date: val}});
    }
    handleTimeChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, time: val}});
    }
    handleNumTicketsChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, numTickets: val}});
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
                ce('Event title: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.title, onChange: (e) => this.handleTitleChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
            ce('Date: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.Date, onChange: (e) => this.handleDateChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
            ce('Time: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.Time, onChange: (e) => this.handleTimeChange(e)}),
            ),ce('div', {className: 'labeled_field'}, 
            ce('# of Tickets: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.NumTickets, onChange: (e) => this.handleNumTicketsChange(e)}),
            ),ce('div', {className: 'labeled_field'}, 
            ce('Description: ',{type:'label'}),
                ce('input', {type: 'text', value: this.state.Description, onChange: (e) => this.handleDescriptionChange(e)}),
            ),
            ce('button', {onClick: (e) => this.handleSubmit(e)}, "Submit"),
        )
    }
}

export default LoginField