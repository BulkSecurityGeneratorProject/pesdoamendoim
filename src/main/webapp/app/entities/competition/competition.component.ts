import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICompetition } from 'app/shared/model/competition.model';
import { AccountService } from 'app/core';
import { CompetitionService } from './competition.service';

@Component({
    selector: 'jhi-competition',
    templateUrl: './competition.component.html'
})
export class CompetitionComponent implements OnInit, OnDestroy {
    competitions: ICompetition[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected competitionService: CompetitionService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.competitionService
            .query()
            .pipe(
                filter((res: HttpResponse<ICompetition[]>) => res.ok),
                map((res: HttpResponse<ICompetition[]>) => res.body)
            )
            .subscribe(
                (res: ICompetition[]) => {
                    this.competitions = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCompetitions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICompetition) {
        return item.id;
    }

    registerChangeInCompetitions() {
        this.eventSubscriber = this.eventManager.subscribe('competitionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
