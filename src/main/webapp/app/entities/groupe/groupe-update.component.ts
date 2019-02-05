import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from './groupe.service';
import { ICompetition } from 'app/shared/model/competition.model';
import { CompetitionService } from 'app/entities/competition';

@Component({
    selector: 'jhi-groupe-update',
    templateUrl: './groupe-update.component.html'
})
export class GroupeUpdateComponent implements OnInit {
    groupe: IGroupe;
    isSaving: boolean;

    competitions: ICompetition[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected groupeService: GroupeService,
        protected competitionService: CompetitionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ groupe }) => {
            this.groupe = groupe;
        });
        this.competitionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICompetition[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICompetition[]>) => response.body)
            )
            .subscribe((res: ICompetition[]) => (this.competitions = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.groupe.id !== undefined) {
            this.subscribeToSaveResponse(this.groupeService.update(this.groupe));
        } else {
            this.subscribeToSaveResponse(this.groupeService.create(this.groupe));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IGroupe>>) {
        result.subscribe((res: HttpResponse<IGroupe>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackCompetitionById(index: number, item: ICompetition) {
        return item.id;
    }
}
