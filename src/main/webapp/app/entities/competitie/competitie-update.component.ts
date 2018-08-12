import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICompetitie } from 'app/shared/model/competitie.model';
import { CompetitieService } from './competitie.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';

@Component({
    selector: 'jhi-competitie-update',
    templateUrl: './competitie-update.component.html'
})
export class CompetitieUpdateComponent implements OnInit {
    private _competitie: ICompetitie;
    isSaving: boolean;

    teams: ITeam[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private competitieService: CompetitieService,
        private teamService: TeamService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ competitie }) => {
            this.competitie = competitie;
        });
        this.teamService.query().subscribe(
            (res: HttpResponse<ITeam[]>) => {
                this.teams = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.competitie.id !== undefined) {
            this.subscribeToSaveResponse(this.competitieService.update(this.competitie));
        } else {
            this.subscribeToSaveResponse(this.competitieService.create(this.competitie));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICompetitie>>) {
        result.subscribe((res: HttpResponse<ICompetitie>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTeamById(index: number, item: ITeam) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get competitie() {
        return this._competitie;
    }

    set competitie(competitie: ICompetitie) {
        this._competitie = competitie;
    }
}
