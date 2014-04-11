/**
 * 
 *
 */

var CATEGORIES = ["JV", "VC", "VO"];
var EVENTS = ["VAULT", "BARS", "BEAM", "FLOOR", "ALLAROUND"];
var TABS = [
            ["jvVault", "jvBars", "jvBeam", "jvFloor", "jvAA"],
            ["vcVault", "vcBars", "vcBeam", "vcFloor", "vcAA"],
            ["voVault", "voBars", "voBeam", "voFloor", "voAA"]
            ];
var RESULT_HEADERS =[
                     ["JV Vault", "JV Bars", "JV Beam", "JV Floor", "JV All-Around"],
                     ["Varsity Compulsory Vault", "Varsity Compulsory Bars", "Varsity Compulsory Beam", "Varsity Compulsory Floor", "Varsity Compulsory All-Around"],
                     ["Varsity Optional Vault", "Varsity Optional Bars", "Varsity Optional Beam", "Varsity Optional Floor", "Varsity Optional All-Around"]
                     ];
var TEAM_EVENTS = ["ALLAROUND","VAULT", "BARS", "BEAM", "FLOOR"];
var TEAM_CATEGORIES = ["JV", "VARSITY"];
var TEAM_TABS = ["jvTeamResults", "varsityTeamResults" ];
var TEAM_HEADERS = {
		"VAULT" : "Vault",
		"BARS" : "Bars",
		"BEAM" : "Beam",
		"FLOOR" : "Floor",
		"ALLAROUND" : "Overall"
};

(function($) {
	$.extend({
		alert: function (message, title, callback) {
			$("<div></div>").dialog( {
				buttons: [{
					text: "OK",
					click: function () { $(this).dialog("close"); }
				}],
				close: function () { $(this).remove(); if (callback) callback();},
				resizable: false,
				title: title ? title : "Error",
				minWidth: 600,
				modal: true
			}).append($("<div/>", {'class': 'messageBox'}).append(message));
		},
		format3: function(val) {
			return (val) ? val.toFixed(3) : '';
		},
		format4: function(val) {
			return (val) ? val.toFixed(4) : '';
		}
	});
})(jQuery);

function EZScoreApp() {
	this.getMeets();
	this.allAroundCells = {};
	$('#meetDate').datepicker();
	$('#indResultsTab').tabs();
	$('#jvResults').tabs();
	$('#vcResults').tabs();
	$('#voResults').tabs();
	$('#teamResultsTab').tabs();
	this.initParameters();

	$('#debug').hide();
}
EZScoreApp.prototype = {
	constructor: EZScoreApp,
	showSpinner: function() {
		$('#loadingMessage').show();
		spinner.spin($('#loadingMessage')[0]);
	},
	hideSpinner: function() {
		spinner.stop();
		$('#loadingMessage').hide();
	},
	initParameters: function() {
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: {action: 'getInitParameters'}
		}).done(function(msg) {
			var parms = $.parseJSON(msg);
			var homeTeam = '';
			if (parms.hasOwnProperty("HOMETEAM")) {
				homeTeam = parms["HOMETEAM"];
			}
			if (parms.hasOwnProperty("TEAMS")) {
				var teams = parms["TEAMS"].split(',');
				for (var i=0; i<teams.length; i++) {
					var team = teams[i];
					var opt = $('<option></option>', {value: team});
					opt.text(team);
					opt.prop('selected', team === homeTeam);
					$('#meetTeams').append(opt);
				}
			}
			$('#meetTeams').multiselect({ selectedList: 6});
		}.bind(this));
	},
	getMeets: function() {
		this.showSpinner();
		this.currentMeet = null;
		$('#indResultsTab').hide();
		$('#teamResultsTab').hide();
		$('#topNav').empty();
		var addMeet = $('<button>', {'type': 'button'}).button({label: 'Add Meet...'});
		addMeet.click(this.addMeetDlg.bind(this));
		$('#topNav').append(addMeet);
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: {action: 'list'}
		}).done(function(msg) {
			this.loadMeets($.parseJSON(msg));
			this.hideSpinner();
		}.bind(this));
	},
	loadMeets: function(meets) {
		$('#root').empty();
		var table = $('<table></table>', {class: 'meetResults'});
		var tbody = $('<tbody></tbody>');
		table.append(tbody);
		var tr = $('<tr>');
		tr.append($('<th>', {class: 'col18px'}).append('&nbsp;'));
		tr.append($('<th>', {class: 'col70pc'}).text('Meet'));
		tr.append($('<th>', {class: 'col30pc'}).text('Date'));
		tbody.append(tr);
		for (var i=0; i<meets.length; i++) {
			tr = $('<tr>');
			var delBtn = $('<img/>', {src: 'trashcan.png', class: 'clickable'});
			delBtn.click({
				meetID: meets[i].ID,
				meetDesc: meets[i].description
				}, this.deleteMeet.bind(this));
			tr.append($('<td>', {class: 'col18px'}).append(delBtn));
			var link = $('<a>', {class: 'clickable'});
			link.text(meets[i].description);
			link.click({id: meets[i].ID}, function(e) {
				this.showMeet(e.data.id);
			}.bind(this));
			tr.append($('<td>', {class: 'col70pc'}).append(link));
			tr.append($('<td>', {class: 'col30pc'}).text(meets[i].date));
			tbody.append(tr);
		}
		$('#root').append(table);
	},
	deleteMeet: function(e) {
		if (confirm('Delete is permanent! Are you sure you want to delete the meet ['+e.data.meetDesc+'] and all of the data?')) {
			this.showSpinner();
			$.ajax({ type: 'POST', url: '/ezscore/ezscore',
				data: {
					action: 'deleteMeet',
					meetID: e.data.meetID
				}
			}).done(function(msg) {
				this.loadMeets($.parseJSON(msg));
				this.hideSpinner();
			}.bind(this));
		}
	},
	meetMenu: function() {
		var btnDiv = $('<div/>', {class: 'dlgRow'});
		var home=$('<button>', {type: 'button'}).button({label: '< Show All Meets'});
		home.click(this.getMeets.bind(this));
		btnDiv.append(home);
		var addComp=$('<button>', {type: 'button'}).button({label: 'Add Competitor...'});
		addComp.click(this.addCompetitorDlg.bind(this));
		btnDiv.append(addComp);
		var indResults=$('<button>', {type: 'button'}).button({label: 'Individual Results'});
		indResults.click(this.showIndResults.bind(this));
		btnDiv.append(indResults);
		var teamResults=$('<button>', {type: 'button'}).button({label: 'Team Results'});
		teamResults.click(this.showTeamResults.bind(this));
		btnDiv.append(teamResults);
		$('#topNav').append(btnDiv);
	},
	addMeetDlg: function() {
		$('#newMeetDlg').dialog({
			autoOpen: true,
			modal: true,
			title: 'Create Meet',
			buttons: [
					{text: "OK", click: this.createMeet.bind(this)},
					{text: "Cancel", click: function() {$('#newMeetDlg').dialog('close');}}
					],
				position: ['center', 'center'],
				minWidth: 600
		});
	},
	createMeet: function() {
		var desc = $('#meetDescription').val();
		var date = $('#meetDate').val();
		var teams = $('#meetTeams').val();
		if (desc.trim().length===0) {
			$.alert("Please enter a Meet description");
			$('#meetDescription').focus();
			return;
		}
		if (date.trim().length===0) {
			$.alert("Please enter the date of the Meet");
			$('#meetDate').focus();
			return;
		}
		if (!teams || teams.length<2) {
			$.alert("Please select at least 2 teams for the Meet");
			$('#meetTeams').focus();
			return;
		}
		this.showSpinner();
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: { 
				'action': "newMeet", 
				'description': desc,
				'date': date,
				'teams' : teams.join()
				}
		})
		.done(function(msg) {
			this.getMeets();
			$('#newMeetDlg').dialog('close');
			this.hideSpinner();
		}.bind(this));
	},
	showMeet: function(meetId) {
		$('#root').empty();
		$('#topNav').empty();
		$('#indResultsTab').hide();
		$('#teamResultsTab').hide();
		this.meetMenu();
		this.showSpinner();
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: { 
				action: "getMeet", 
				order: 'team',
				meetID: meetId 
				}
		})
		.done(function(msg) {
			var meet = $.parseJSON(msg);
			this.currentMeet = meet;
			var table = $('<table></table>', {class: 'meetResults'});
			var tbody = $('<tbody></tbody>');
			var tr;
			var headrow = $('<tr>');
			headrow.append($('<th/>', {class: 'col18px'}).append('&nbsp;'));
			headrow.append($('<th/>', {class: 'col10pc'}).append('#'));
			headrow.append($('<th/>', {class: 'col20pc'}).append('Name'));
			headrow.append($('<th/>', {class: 'col15pc'}).append('Team'));
			headrow.append($('<th/>', {class: 'col5pc'}).append('Category'));
			headrow.append($('<th/>', {class: 'col10pc'}).append('Vault'));
			headrow.append($('<th/>', {class: 'col10pc'}).append('Bars'));
			headrow.append($('<th/>', {class: 'col10pc'}).append('Beam'));
			headrow.append($('<th/>', {class: 'col10pc'}).append('Floor'));
			headrow.append($('<th/>', {class: 'col10pc'}).append('All-Around'));
			tbody.append(headrow);
			table.append(tbody);			
			$('#root').append(table);
			var competitors = [];
			for (var cname in meet.competitors) {
				if (!meet.competitors.hasOwnProperty(cname)) continue;
				competitors.push(meet.competitors[cname]);
			}
			competitors.sort(function(a,b) {
				if (a.number<b.number) return -1;
				else if (a.number === b.number) return 0;
				else return 1;
			});
			for (var i=0; i<competitors.length; i++) {
				var competitor = competitors[i];
				tr = $('<tr></tr>');
				if (i%2) {
					tr.addClass('altRow');
				}
				var delBtn = $('<img/>', {src: 'trashcan.png', class: 'clickable'});
				delBtn.click(
						{
							number: competitor.number,
							name: competitor.name
						}, 
						this.deleteCompetitor.bind(this));
				tr.append($('<td>', {class: 'col18px'}).append(delBtn));
				tr.append(this.createCompCell(competitor, 'clickable col10pc', competitor.number));
				tr.append(this.createCompCell(competitor, 'clickable col20pc', competitor.name));
				tr.append(this.createCompCell(competitor, 'clickable col15pc', competitor.team));
				tr.append(this.createCompCell(competitor, 'clickable col5pc', competitor.category));
				
				tr.append(this.createScoreCell(meet.ID, competitor,'VAULT'));
				tr.append(this.createScoreCell(meet.ID, competitor,'BARS'));
				tr.append(this.createScoreCell(meet.ID, competitor,'BEAM'));
				tr.append(this.createScoreCell(meet.ID, competitor,'FLOOR'));
				tr.append(this.createAllAroundCell(competitor));
				tbody.append(tr);
			}
			this.hideSpinner();
		}.bind(this));
	},
	deleteCompetitor: function(e) {
		if (confirm('Delete is permanent! Are you sure you want to delete the competitor ['+e.data.name+'] and all scores?')) {
			this.showSpinner();
			$.ajax({ type: 'POST', url: '/ezscore/ezscore',
				data: { 
					action: "deleteCompetitor", 
					meetID: this.currentMeet.ID,
					number: e.data.number
					}
			})
			.done(function(msg) {
				var meet = $.parseJSON(msg);
				this.currentMeet = meet;
				this.showMeet(this.currentMeet.ID);
				this.hideSpinner();
			}.bind(this));
		}
	},
	createCompCell: function(competitor, className, text) {
		var cell = $('<td/>', {'class': className}).append(text);
		cell.click({competitor: competitor}, this.editCompetitor.bind(this));
		return cell;
	},
	editCompetitor: function(e) {
		this.showCompetitorDlg(e.data.competitor);
	},
	showCompetitorDlg: function(competitor) {
		var isAdd = competitor === undefined;
		var title;
		var btnText;
		var teamSel = $('#compTeam');
		teamSel.empty();
		teamSel.append(new Option('- Select -', ''));
		for (var i=0; i<this.currentMeet.teams.length; i++) {
			teamSel.append(new Option(this.currentMeet.teams[i]));
		}
		if (isAdd) {
			title = 'Add Competitor';
			btnText = 'Add and Close';
			$('#compNumber').prop('disabled', false);
			$('#compNumber').val('');
			$('#compName').val('');
			$('#compTeam').val('');
			$('#compCategory').val('');
		} else {
			title = 'Edit Competitor';
			btnText = 'OK';
			$('#compNumber').val(competitor.number);
			$('#compNumber').prop('disabled', true);
			$('#compName').val(competitor.name);
			$('#compTeam').val(competitor.team);
			$('#compCategory').val(competitor.category);
		}
		var buttons = [];
		if (isAdd) {
			buttons.push({
				text: "Add and Continue", click: function(e) { this.createCompetitor(true);}.bind(this)
			});
		}
		buttons.push({
			text: btnText, click: function(e) {
				if (isAdd) this.createCompetitor();
				else this.updateCompetitor(competitor);
			}.bind(this)
		});
		buttons.push({text: "Cancel", click: function() {$('#newCompetitorDlg').dialog('close');}});
		$('#newCompetitorDlg').dialog({
			autoOpen: true,
			modal: true,
			title: title,
			buttons: buttons,
			minWidth: 600
		});
	},
	addCompetitorDlg: function(e) {
		this.showCompetitorDlg();
	},
	updateCompetitor: function(competitor) {
		var name = $('#compName').val();
		var team = $('#compTeam').val();
		var category = $('#compCategory').val();
		if (!this.validateCompetitor(competitor)) {
			return;
		}
		this.showSpinner();
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: { 
				'action': "updateCompetitor", 
				'meetID': this.currentMeet.ID,
				'name': name ,
				'number': competitor.number,
				'team' : team,
				'category' : category
				}
		})
		.done(function(msg) {
			this.showMeet(this.currentMeet.ID);
			$('#newCompetitorDlg').dialog('close');
			this.hideSpinner();
		}.bind(this));
		
	},
	validateCompetitor: function(competitor) {
		var num = $('#compNumber').val();
		var name = $('#compName').val();
		var team = $('#compTeam').val();
		var category = $('#compCategory').val();
		if (!num || !$.isNumeric(num)) {
			$.alert('Please enter a number for Competitor Number', 'Error', function() {$('#compNumber').select().focus();});
			return false;
		}
		if (!competitor && this.meetHasCompetitor(num)) {
			$.alert('The Competitor Number you entered is already in use.<br/>Please enter a unique Competitor Number', 'Error', 
					function() {$('#compNumber').select().focus();});
			return false;
		}
		if (!name) {
			$.alert('Please enter a value for Competitor Name', 'Error', function() {$('#compName').select().focus();});
			return false;
		}
		if (!team) {
			$.alert('Please select a Team', 'Error', function() {$('#compTeam').select().focus();});
			return false;
		}
		if (!category) {
			$.alert('Please select the competitor\'s Category', 'Error', function() {$('#compCategory').focus();});
			return false;
		}
		return true;
	},
	createCompetitor: function(cont) {
		var num = $('#compNumber').val();
		var name = $('#compName').val();
		var team = $('#compTeam').val();
		var category = $('#compCategory').val();
		if (!this.validateCompetitor()) {
			return;
		}
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: { 
				'action': "addCompetitor", 
				'meetID': this.currentMeet.ID,
				'name': name ,
				'number': num,
				'team' : team,
				'category' : category
				}
		})
		.done(function(msg) {
			this.showMeet(this.currentMeet.ID);
			if (cont) {
				$('#compName').val('');
				$('#compNumber').val(parseInt(num)+1);
			} else {
				$('#newCompetitorDlg').dialog('close');
			}
		}.bind(this));
	},
	meetHasCompetitor: function(num) {
		for (var compNum in this.currentMeet.competitors) {			
			if (!this.currentMeet.competitors.hasOwnProperty(compNum)) continue;
			if (compNum === num) return true;
		}
		return false;
	},
	createScoreCell: function(meetID, competitor, event) {
		var cell = $('<td/>', {class: 'clickable col10pc'});
		cell.append($('<div/>', {class: 'scoreCell'}).append($.format3(competitor.competitorResults[event])));
		cell.click({meetID: meetID, event: event, competitor: competitor}, this.editScore.bind(this));
		return cell;
	},
	createAllAroundCell: function(competitor) {
		this.allAroundCells[competitor.number] = $('<div/>', {class: 'scoreCell'}).append($.format4(competitor.competitorResults['ALLAROUND']));
		var cell = $('<td/>').append(this.allAroundCells[competitor.number]);
		return cell;
	},
	editScore: function(e) {
		cell = $(e.target).parents('td');
		cell.empty();
		var score = e.data.competitor.competitorResults[e.data.event];
		var input = $('<input/>', {type: 'text', value: score, class: 'scoreInput'});
		input.change({
			meetID: e.data.meetID, 
			event: e.data.event, 
			competitor: e.data.competitor
			}, this.updateScore.bind(this));
		cell.append(input);
		input.keyup({score: score}, function(e) {
			if (e.keyCode == 27) {
				this.hideInput($(e.target), e.data.score);
			}
		}.bind(this));
		input.blur({score: score}, this.cancelUpdate.bind(this));
		input.focus().select();
	},
	cancelUpdate: function(e) {
		if ($('#debugDisableBlur').is(':checked')) return;
		var input = $(e.target);
		var newValue = input.val();
		if ((!e.data.score && !newValue) || (newValue==e.data.score)) {
			this.hideInput(input, e.data.score);
		}
	},
	hideInput: function(input, score) {
		var cell = input.parents('td');
		cell.empty();
		cell.append($('<div/>', {class: 'scoreCell'}).append($.format3(score)));
	},
	isValidScore: function(score) {
		if (score==='') return true;
		var valid = (score.match(/^\d*(\.\d+)?$/));
		if (!valid) return false;
		var ns = parseFloat(score);
		return (ns>0 && ns<=10);
	},
	updateScore: function(e) {
		var input = $(e.target);
		var newScore = input.val();
		console.log("updateScore called");
		if (!this.isValidScore(newScore)) {
			$.alert('Please enter a valid score between 0 and 10', 'Error', function() {
				input.select().focus();
			});
			return;
		}
		var cell = input.parents('td');
		this.showSpinner();
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: { 
				action: "updateScore", 
				meetID: e.data.meetID,
				competitorNumber: e.data.competitor.number,
				event: e.data.event,
				score: input.val()
				}
		})
		.done(function(msg) {
			var newComp = $.parseJSON(msg);
			cell.empty();
			cell.append($('<div/>', {class: 'scoreCell'}).append($.format3(newComp.competitorResults[e.data.event])));
			this.allAroundCells[newComp.number].empty().append($.format4(newComp.competitorResults['ALLAROUND']));
			e.data.competitor.competitorResults[e.data.event] = newComp.competitorResults[e.data.event];
			e.data.competitor.competitorResults['ALLAROUND'] = newComp.competitorResults['ALLAROUND'];
			this.hideSpinner();
		}.bind(this));
	},
	showIndResults: function() {
		this.showSpinner();
		$('#topNav').empty();
		this.resultsMenu();
		$('#root').empty();
		$('#teamResultsTab').hide();
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: { 
				action: "individualResults", 
				meetID: this.currentMeet.ID
				}
		})
		.done(function(msg) {
			var tabs = $('#indResultsTab');
			var results = $.parseJSON(msg);
			for (var i=0; i<CATEGORIES.length; i++) {
				var cat = CATEGORIES[i];
				for (var j=0; j<EVENTS.length; j++) {
					var evt = EVENTS[j];
					var table = $('<table></table>', {class: 'meetResults'});
					var tbody = $('<tbody></tbody>');
					var tr;
					tbody.append($('<tr>').append($('<td>', {colspan: 5, class: 'resultsHeader'}).append(RESULT_HEADERS[i][j])));
					var headrow = $('<tr>');
					headrow.append($('<th>', {class: 'col10pc'}).text('Place'));
					headrow.append($('<th>', {class: 'col10pc'}).text('#'));
					headrow.append($('<th>').text('Name'));
					headrow.append($('<th>', {class: 'col15pc'}).text('Team'));
					headrow.append($('<th>', {class: 'col10pc'}).text('Score'));
					tbody.append(headrow);
					var lastScore = null;
					var place = 1;
					if (results['results'][cat] && results['results'][cat]['results'][evt]) {
						var scores = results['results'][cat]['results'][evt];
						for (var k=0; k<scores.length; k++) {
							if (lastScore!=null && lastScore !== scores[k].score) {
								place=(k+1);
							}
							tr = $('<tr>');
							if (k%2) {
								tr.addClass('altRow');
							}
							tr.append($('<td>', {class: 'col10pc'}).text(place));
							tr.append($('<td>', {class: 'col10pc'}).text(scores[k].number));
							tr.append($('<td>').text(scores[k].name));
							tr.append($('<td>', {class: 'col15pc'}).text(scores[k].team));
							tr.append($('<td>', {class: 'col10pc'}).text($.format4(scores[k].score)));
							lastScore = scores[k].score;
							tbody.append(tr);
						}
					} else {
						tr = $('<tr>');
						tr.append($('<td/>', {colspan: 5}).text('No results available'));
						tbody.append(tr);
					}
					table.append(tbody);
					$('#'+TABS[i][j]).empty().append(table);
				}
			}
			tabs.show();
			this.hideSpinner();
		}.bind(this));
	},
	showTeamResults: function() {
		this.showSpinner();
		$('#topNav').empty();
		this.resultsMenu(true);
		$('#root').empty();
		$('#indResultsTab').hide();
		$.ajax({ type: 'POST', url: '/ezscore/ezscore',
			data: { 
				action: "teamResults", 
				meetID: this.currentMeet.ID
				}
		})
		.done(function(msg) {
			var tabs = $('#teamResultsTab');
			var results = $.parseJSON(msg);
			for (var i=0; i<TEAM_CATEGORIES.length; i++) {
				$('#'+TEAM_TABS[i]).empty();
				var cat = TEAM_CATEGORIES[i];
				if (cat === "VARSITY") {
					$('#'+TEAM_TABS[i]).append($('<p>').append('* indicates Varsity Optional score'));
				}
				for (var j=0; j<TEAM_EVENTS.length; j++) {
					var evt = TEAM_EVENTS[j];
					var table = $('<table></table>', {class: 'meetResults'});
					var tbody = $('<tbody></tbody>');
					var tr;
					tbody.append($('<tr>').append($('<td>', {colspan: 3, class: 'resultsHeader'}).append(TEAM_HEADERS[evt])));
					var headrow = $('<tr>');
					headrow.append($('<th>', {class: 'col10pc'}).text('Place'));
					headrow.append($('<th>', {class: 'col15pc'}).text('Team'));
					headrow.append($('<th>', {class: 'col10pc'}).text('Score'));
					tbody.append(headrow);
					var lastScore = null;
					var place = 1;
					if (results['teamResults'][cat] && results['teamResults'][cat][evt]) {
						var scores = results['teamResults'][cat][evt];
						for (var k=0; k<scores.length; k++) {
							if (lastScore!=null && lastScore !== scores[k].score) {
								++place;
							}
							tr = $('<tr>');
							if (k%2) {
								tr.addClass('altRow');
							}
							tr.append($('<td>', {class: 'col10pc'}).text(place));
							tr.append($('<td>').text(scores[k].team));
							tr.append($('<td>', {class: 'col10pc'}).append($.format4(scores[k].score)));
							lastScore = scores[k].score;
							tbody.append(tr);
							if (scores[k].includedScores && evt !== "ALLAROUND") {
								this.writeTeamIndividualScores(tbody, scores[k].includedScores);
							}
						}
					} else {
						tr = $('<tr>');
						tr.append($('<td/>', {colspan: 3}).text('No results available'));
						tbody.append(tr);
					}
					table.append(tbody);
					$('#'+TEAM_TABS[i]).append(table);
					$('#'+TEAM_TABS[i]).append('<hr/>');
				}
			}		
			tabs.show();
			this.hideSpinner();
		}.bind(this));
	},
	writeTeamIndividualScores: function(tbody, indScores) {
		var tr,cellClass;
		for (var i=0; i<indScores.length; i++) {
			cellClass = indScores[i].isOptional ? "optScore" : "compScore"; 
			tr = $('<tr>');
			tr.append($('<td>').append('&nbsp;'));
			tr.append($('<td>', {'class': cellClass}).append(indScores[i].name));
			tr.append($('<td>').append($.format3(indScores[i].score)));
			tbody.append(tr);
		}
		
	},
	resultsMenu: function(isTeam) {
		var btnDiv = $('<div/>', {class: 'dlgRow'});
		var back=$('<button>', {type: 'button'}).button({label: '< Back to Meet'});
		back.click(this.backToMeet.bind(this));
		btnDiv.append(back);
		if (isTeam) {
			var indResults=$('<button>', {type: 'button'}).button({label: 'Show Individual Results'});
			indResults.click(this.showIndResults.bind(this));
			btnDiv.append(indResults);
		} else {
			var teamResults=$('<button>', {type: 'button'}).button({label: 'Show Team Results'});
			teamResults.click(this.showTeamResults.bind(this));
			btnDiv.append(teamResults);
		}
		$('#topNav').append(btnDiv);
	},
	backToMeet: function(e) {
		this.showMeet(this.currentMeet.ID);
	}
};