import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IRound } from 'app/shared/model/round.model';
import { RoundService } from './round.service';
import { IGroupe } from 'app/shared/model/groupe.model';
import { GroupeService } from 'app/entities/groupe';

@Component({
    selector: 'jhi-round-update',
    templateUrl: './round-update.component.html'
})
export class RoundUpdateComponent implements OnInit {
    round: IRound;
    isSaving: boolean;

    groupes: IGroupe[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected roundService: RoundService,
        protected groupeService: GroupeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ round }) => {
            this.round = round;
        });
        this.groupeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IGroupe[]>) => mayBeOk.ok),
                map((response: HttpResponse<IGroupe[]>) => response.body)
            )
            .subscribe((res: IGroupe[]) => (this.groupes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.round.id !== undefined) {
            this.subscribeToSaveResponse(this.roundService.update(this.round));
        } else {
            this.subscribeToSaveResponse(this.roundService.create(this.round));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRound>>) {
        result.subscribe((res: HttpResponse<IRound>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGroupeById(index: number, item: IGroupe) {
        return item.id;
    }
}
