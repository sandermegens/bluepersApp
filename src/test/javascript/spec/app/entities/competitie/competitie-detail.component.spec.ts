/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BluepersAppTestModule } from '../../../test.module';
import { CompetitieDetailComponent } from 'app/entities/competitie/competitie-detail.component';
import { Competitie } from 'app/shared/model/competitie.model';

describe('Component Tests', () => {
    describe('Competitie Management Detail Component', () => {
        let comp: CompetitieDetailComponent;
        let fixture: ComponentFixture<CompetitieDetailComponent>;
        const route = ({ data: of({ competitie: new Competitie(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BluepersAppTestModule],
                declarations: [CompetitieDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CompetitieDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CompetitieDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.competitie).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
