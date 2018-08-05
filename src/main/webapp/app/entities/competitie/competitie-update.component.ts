import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICompetitie } from 'app/shared/model/competitie.model';
import { CompetitieService } from './competitie.service';

@Component({
    selector: 'jhi-competitie-update',
    templateUrl: './competitie-update.component.html'
})
export class CompetitieUpdateComponent implements OnInit {
    private _competitie: ICompetitie;
    isSaving: boolean;

    constructor(private competitieService: CompetitieService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ competitie }) => {
            this.competitie = competitie;
        });
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
    get competitie() {
        return this._competitie;
    }

    set competitie(competitie: ICompetitie) {
        this._competitie = competitie;
    }
}
