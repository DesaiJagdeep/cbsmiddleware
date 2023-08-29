import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICourtCaseSetting, NewCourtCaseSetting } from '../court-case-setting.model';

export type PartialUpdateCourtCaseSetting = Partial<ICourtCaseSetting> & Pick<ICourtCaseSetting, 'id'>;

type RestOf<T extends ICourtCaseSetting | NewCourtCaseSetting> = Omit<
  T,
  'dinank' | 'tharavDinank' | 'karjFedNotice' | 'oneZeroOneNoticeOne' | 'oneZeroOneNoticeTwo' | 'maganiNotice'
> & {
  dinank?: string | null;
  tharavDinank?: string | null;
  karjFedNotice?: string | null;
  oneZeroOneNoticeOne?: string | null;
  oneZeroOneNoticeTwo?: string | null;
  maganiNotice?: string | null;
};

export type RestCourtCaseSetting = RestOf<ICourtCaseSetting>;

export type NewRestCourtCaseSetting = RestOf<NewCourtCaseSetting>;

export type PartialUpdateRestCourtCaseSetting = RestOf<PartialUpdateCourtCaseSetting>;

export type EntityResponseType = HttpResponse<ICourtCaseSetting>;
export type EntityArrayResponseType = HttpResponse<ICourtCaseSetting[]>;

@Injectable({ providedIn: 'root' })
export class CourtCaseSettingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/court-case-settings');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(courtCaseSetting: NewCourtCaseSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCaseSetting);
    return this.http
      .post<RestCourtCaseSetting>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(courtCaseSetting: ICourtCaseSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCaseSetting);
    return this.http
      .put<RestCourtCaseSetting>(`${this.resourceUrl}/${this.getCourtCaseSettingIdentifier(courtCaseSetting)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(courtCaseSetting: PartialUpdateCourtCaseSetting): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(courtCaseSetting);
    return this.http
      .patch<RestCourtCaseSetting>(`${this.resourceUrl}/${this.getCourtCaseSettingIdentifier(courtCaseSetting)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCourtCaseSetting>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCourtCaseSetting[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCourtCaseSettingIdentifier(courtCaseSetting: Pick<ICourtCaseSetting, 'id'>): number {
    return courtCaseSetting.id;
  }

  compareCourtCaseSetting(o1: Pick<ICourtCaseSetting, 'id'> | null, o2: Pick<ICourtCaseSetting, 'id'> | null): boolean {
    return o1 && o2 ? this.getCourtCaseSettingIdentifier(o1) === this.getCourtCaseSettingIdentifier(o2) : o1 === o2;
  }

  addCourtCaseSettingToCollectionIfMissing<Type extends Pick<ICourtCaseSetting, 'id'>>(
    courtCaseSettingCollection: Type[],
    ...courtCaseSettingsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const courtCaseSettings: Type[] = courtCaseSettingsToCheck.filter(isPresent);
    if (courtCaseSettings.length > 0) {
      const courtCaseSettingCollectionIdentifiers = courtCaseSettingCollection.map(
        courtCaseSettingItem => this.getCourtCaseSettingIdentifier(courtCaseSettingItem)!
      );
      const courtCaseSettingsToAdd = courtCaseSettings.filter(courtCaseSettingItem => {
        const courtCaseSettingIdentifier = this.getCourtCaseSettingIdentifier(courtCaseSettingItem);
        if (courtCaseSettingCollectionIdentifiers.includes(courtCaseSettingIdentifier)) {
          return false;
        }
        courtCaseSettingCollectionIdentifiers.push(courtCaseSettingIdentifier);
        return true;
      });
      return [...courtCaseSettingsToAdd, ...courtCaseSettingCollection];
    }
    return courtCaseSettingCollection;
  }

  protected convertDateFromClient<T extends ICourtCaseSetting | NewCourtCaseSetting | PartialUpdateCourtCaseSetting>(
    courtCaseSetting: T
  ): RestOf<T> {
    return {
      ...courtCaseSetting,
      dinank: courtCaseSetting.dinank?.toJSON() ?? null,
      tharavDinank: courtCaseSetting.tharavDinank?.toJSON() ?? null,
      karjFedNotice: courtCaseSetting.karjFedNotice?.toJSON() ?? null,
      oneZeroOneNoticeOne: courtCaseSetting.oneZeroOneNoticeOne?.toJSON() ?? null,
      oneZeroOneNoticeTwo: courtCaseSetting.oneZeroOneNoticeTwo?.toJSON() ?? null,
      maganiNotice: courtCaseSetting.maganiNotice?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCourtCaseSetting: RestCourtCaseSetting): ICourtCaseSetting {
    return {
      ...restCourtCaseSetting,
      dinank: restCourtCaseSetting.dinank ? dayjs(restCourtCaseSetting.dinank) : undefined,
      tharavDinank: restCourtCaseSetting.tharavDinank ? dayjs(restCourtCaseSetting.tharavDinank) : undefined,
      karjFedNotice: restCourtCaseSetting.karjFedNotice ? dayjs(restCourtCaseSetting.karjFedNotice) : undefined,
      oneZeroOneNoticeOne: restCourtCaseSetting.oneZeroOneNoticeOne ? dayjs(restCourtCaseSetting.oneZeroOneNoticeOne) : undefined,
      oneZeroOneNoticeTwo: restCourtCaseSetting.oneZeroOneNoticeTwo ? dayjs(restCourtCaseSetting.oneZeroOneNoticeTwo) : undefined,
      maganiNotice: restCourtCaseSetting.maganiNotice ? dayjs(restCourtCaseSetting.maganiNotice) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCourtCaseSetting>): HttpResponse<ICourtCaseSetting> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCourtCaseSetting[]>): HttpResponse<ICourtCaseSetting[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
